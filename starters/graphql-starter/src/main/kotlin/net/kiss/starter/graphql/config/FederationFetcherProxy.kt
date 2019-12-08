package net.kiss.starter.graphql.config

import com.apollographql.federation.graphqljava._Entity
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import mu.KotlinLogging
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationRequestItem
import net.kiss.starter.graphql.dsl.data.FederationResponse
import java.lang.IllegalStateException
import kotlin.reflect.KClass

class FederationFetcherProxy(
  private val resolvers: Map<String, GraphQLFederation<*, *>>,
  private val mapper: ObjectMapper
) : DataFetcher<Any> {
  val logger = KotlinLogging.logger { }

  override fun get(env: DataFetchingEnvironment): Any {
    val request = env.getArgument<List<Map<String, Any>>>(_Entity.argumentName)

    return runBlocking(MDCContext()) {
      logger.info { "Federation request for entity $request" }
      return@runBlocking request.asSequence()
        .mapIndexed { i, data -> FederationRequestItem<Any>(i, data, mapper) }
        .groupBy { it.data["__typename"] }
        .map {
          async {
            val resolver = resolvers[it.key] ?: throw IllegalStateException()
            @Suppress("UNCHECKED_CAST")
            callResolver(resolver as GraphQLFederation<Any, Any>, it.value)
          }
        }
        .flatMap { it.await().items }
        .sortedBy { it.index }
        .map { it.result }
        .toList()
        .also {
          logger.info { "Federation resolution $it" }
        }
    }
  }

  private suspend fun callResolver(
    resolver: GraphQLFederation<Any, Any>, items: List<FederationRequestItem<Any>>
  ): FederationResponse<*> {
    return resolver.resolver.invoke(FederationRequest(resolver.keyType, items))
  }
}

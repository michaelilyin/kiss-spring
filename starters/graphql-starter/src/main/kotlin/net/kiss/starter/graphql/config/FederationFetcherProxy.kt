package net.kiss.starter.graphql.config

import com.apollographql.federation.graphqljava._Entity
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationRequestItem
import net.kiss.starter.graphql.dsl.data.FederationResponse
import java.lang.IllegalStateException
import kotlin.reflect.KClass

class FederationFetcherProxy(
  private val resolvers: Map<String, GraphQLFederation<*, *>>
) : DataFetcher<Any> {
  override fun get(env: DataFetchingEnvironment): Any {
    val request = env.getArgument<List<Map<String, Any>>>(_Entity.argumentName)

    return runBlocking(MDCContext()) {
      return@runBlocking request.asSequence()
        .mapIndexed { i, data -> FederationRequestItem<Any>(i, data) }
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
    }
  }

  private suspend fun callResolver(
    resolver: GraphQLFederation<Any, Any>, items: List<FederationRequestItem<Any>>
  ): FederationResponse<*> {
    return resolver.resolver.invoke(FederationRequest(resolver.keyType, items))
  }
}

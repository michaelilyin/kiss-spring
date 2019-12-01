package net.kiss.starter.graphql.config

import com.apollographql.federation.graphqljava._Entity
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import java.lang.IllegalStateException

data class FederationRequestItem(
  val index: Int,
  val data: Map<String, Any>
)

data class FederationRequestResult<T>(
  val index: Int,
  val result: T
)

class FederationFetcherProxy(
  private val resolvers: Map<String, GraphQLFederation<*>>
) : DataFetcher<Any> {
  override fun get(env: DataFetchingEnvironment): Any {
    val request = env.getArgument<List<Map<String, Any>>>(_Entity.argumentName)

    return runBlocking(MDCContext()) {
      return@runBlocking request.asSequence()
        .mapIndexed { i, data -> FederationRequestItem(i, data) }
        .groupBy { it.data["__typename"] }
        .map {
          val resolver = resolvers[it.key] ?: throw IllegalStateException()
          async {
            return@async resolver.resolver(FederationRequest.from(it.value))
          }
        }
        .flatMap { it.await().items }
        .sortedBy { it.index }
        .map { it.result }
    }
  }
}

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
import net.kiss.starter.graphql.dsl.data.FederationResponse
import java.lang.IllegalStateException

data class FederationRequestItem<T>(
  val index: Int,
  val data: Map<String, Any>
) {
  val key: T
    get() {
      TODO()
    }
}

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
        .mapIndexed { i, data -> FederationRequestItem<Any>(i, data) }
        .groupBy { it.data["__typename"] }
        .map { async { callResolver(it) } }
        .flatMap { it.await().items }
        .sortedBy { it.index }
        .map { it.result }
    }
  }

  private suspend fun callResolver(
    item: Map.Entry<Any?, List<FederationRequestItem<Any>>>
  ): FederationResponse<Any, out Any?> {
    val resolver = resolvers[item.key] ?: throw IllegalStateException()
    val reqItems = item.value

    return resolver.resolver(FederationRequest.from(reqItems))
  }
}

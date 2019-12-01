package net.kiss.starter.graphql.config

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

class FetcherProxy<T>(
  private val target: suspend (GraphQLRequest) -> T
) : DataFetcher<T> {
  override fun get(environment: DataFetchingEnvironment): T {
    return runBlocking(MDCContext()) {
      return@runBlocking target(GraphQLRequest.from(environment))
    }
  }
}

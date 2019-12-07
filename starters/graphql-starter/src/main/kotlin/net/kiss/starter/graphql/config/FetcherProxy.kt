package net.kiss.starter.graphql.config

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

class FetcherProxy<C: Any, A: Any, T>(
  private val field: GraphQLObjectField<C, A, T>
) : DataFetcher<T> {
  override fun get(environment: DataFetchingEnvironment): T {
    return runBlocking(MDCContext()) {
      val request = GraphQLRequest<C, A>(field.argType, environment)
      return@runBlocking field.fetcher.invoke(request)
    }
  }
}

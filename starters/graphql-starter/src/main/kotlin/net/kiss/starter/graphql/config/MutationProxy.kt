package net.kiss.starter.graphql.config

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import net.kiss.starter.graphql.dsl.common.GraphQLMutationAction
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

class MutationProxy<C: Any, I: Any, T>(
  private val field: GraphQLMutationAction<C, I, T>
) : DataFetcher<T> {
  override fun get(environment: DataFetchingEnvironment): T {
    return runBlocking(MDCContext()) {
      val request = GraphQLRequest<C, I>(field.argType, environment)
      return@runBlocking field.fetcher.invoke(request)
    }
  }
}

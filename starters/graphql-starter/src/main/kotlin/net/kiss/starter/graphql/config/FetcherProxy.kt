package net.kiss.starter.graphql.config

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import mu.KotlinLogging
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

class FetcherProxy<C: Any, A: Any, T>(
  private val field: GraphQLObjectField<C, A, T>,
  private val mapper: ObjectMapper
) : DataFetcher<T> {
  private val logger = KotlinLogging.logger {  }

  override fun get(environment: DataFetchingEnvironment): T {
    return runBlocking(MDCContext()) {
      val request = GraphQLRequest<C, A>(field.contextType, field.argType, environment, mapper)
      logger.info { "Call query for ${environment.field.name} with argument ${request.arg} and context ${request.sourceOrNull}" }
      return@runBlocking field.fetcher.invoke(request).also {
        logger.info { "Result is $it" }
      }
    }
  }
}

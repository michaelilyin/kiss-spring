package net.kiss.starter.graphql.dsl.data

import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KClass

class GraphQLRequest (
  private val env: DataFetchingEnvironment
) {
  companion object {
    fun from(env: DataFetchingEnvironment): GraphQLRequest {
      return GraphQLRequest(env)
    }
  }

  fun hasArg(name: String): Boolean = env.containsArgument(name)

  inline fun <reified T : Any> arg(name: String): T {
    return arg(name, T::class)
  }

  fun <T : Any> arg(name: String, type: KClass<T>): T {
    TODO()
  }

}

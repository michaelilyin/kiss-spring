package net.kiss.starter.graphql.dsl.data

import kotlin.reflect.KClass

class GraphQLRequest {
  fun hasArg(name: String): Boolean {
    TODO()
  }

  inline fun <reified T : Any> arg(name: String): T {
    return arg(name, T::class)
  }

  fun <T : Any> arg(name: String, type: KClass<T>): T {
    TODO()
  }

}

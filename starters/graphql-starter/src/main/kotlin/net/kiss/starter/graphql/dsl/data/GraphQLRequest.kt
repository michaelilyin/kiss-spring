package net.kiss.starter.graphql.dsl.data

import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

class GraphQLRequest<C: Any, A: Any> (
  private val argType: KClass<A>,
  private val env: DataFetchingEnvironment
) {
  val arg: A
    get() {
      val constructor = argType.primaryConstructor ?: throw IllegalArgumentException()
      val params = mutableMapOf<KParameter, Any?>()
      constructor.parameters.forEach {
        params[it] = env.arguments[it.name]
      }

      return constructor.callBy(params)
    }

  val context: C
    get() {
      return env.getContext<C>()
    }

}

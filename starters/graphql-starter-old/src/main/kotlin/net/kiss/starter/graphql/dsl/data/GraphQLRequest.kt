package net.kiss.starter.graphql.dsl.data

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KClass

class GraphQLRequest<C : Any, A : Any>(
  private val contextType: KClass<C>,
  private val argType: KClass<A>,
  private val env: DataFetchingEnvironment,
  private val mapper: ObjectMapper
) {
  val arg: A by lazy { mapper.convertValue<A>(env.arguments, argType.javaObjectType) }

  val source: C
    get() {
      return sourceOrNull!!
    }

  val sourceOrNull: C? by lazy {
    val src = env.getSource<Any?>()
    @Suppress("UNCHECKED_CAST")
    when {
      src == null -> null
      contextType.isInstance(src) -> src as C
      src is Map<*, *> -> mapper.convertValue<C>(src, contextType.javaObjectType)
      else -> throw IllegalArgumentException()
    }
  }

}

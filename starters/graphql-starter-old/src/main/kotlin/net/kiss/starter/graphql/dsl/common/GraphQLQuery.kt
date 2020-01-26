package net.kiss.starter.graphql.dsl.common

import kotlin.reflect.KClass

open class GraphQLQuery<T: Any>(
  val type: KClass<T>,
  val parent: GraphQLType<T>
) {
  val fields = mutableMapOf<String, GraphQLObjectField<T, *, *>>()

  protected fun <K : Any, F> addField(field: String, context: GraphQLObjectField<T, K, F>) {
    if (fields.containsKey(field)) throw IllegalArgumentException()
    fields[field] = context
  }

  protected fun mergeSuper(
    source: GraphQLQuery<T>,
    destination: GraphQLQuery<T>
  ) {
    val common = source.fields.keys.intersect(fields.keys)
    if (common.isNotEmpty()) {
      throw java.lang.IllegalArgumentException()
    }

    destination.fields.putAll(source.fields)
  }
}

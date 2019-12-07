package net.kiss.starter.graphql.dsl.common

import kotlin.reflect.KClass

open class GraphQLMutation<T : Any>(
  val type: KClass<T>,
  val parent: GraphQLType<T>
) {
  val fields = mutableMapOf<String, GraphQLMutationAction<T, *, *>>()

  protected fun <I : Any, F> addFieldMutation(field: String, context: GraphQLMutationAction<T, I, F>) {
    if (fields.containsKey(field)) throw IllegalArgumentException()
    fields[field] = context
  }
}

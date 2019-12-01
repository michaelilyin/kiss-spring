package net.kiss.starter.graphql.dsl.common

open class GraphQLMutation<T>(
  val parent: GraphQLType<T>
) {
  val fields = mutableMapOf<String, GraphQLMutationAction<T, *>>()

  protected fun <F> addFieldMutation(field: String, context: GraphQLMutationAction<T, F>) {
    if (fields.containsKey(field)) throw IllegalArgumentException()
    fields[field] = context
  }
}

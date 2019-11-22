package net.kiss.starter.graphql.dsl.common

open class GraphQLMutation<T>(
  private val parent: GraphQLType<T>
) {
  private val fields = mutableMapOf<String, GraphQLMutationAction<T, *>>()

  protected fun <F> addFieldMutation(field: String, context: GraphQLMutationAction<T, F>) {
    if (fields.containsKey(field)) throw IllegalArgumentException()
    fields[field] = context
  }
}

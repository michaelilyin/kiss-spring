package net.kiss.starter.graphql.dsl.common

open class GraphQLQuery<T>(
  private val parent: GraphQLType<T>
) {
  private val fields = mutableMapOf<String, GraphQLObjectField<T, *>>()

  protected fun <F> addField(field: String, context: GraphQLObjectField<T, F>) {
    if (fields.containsKey(field)) throw IllegalArgumentException()
    fields[field] = context
  }
}

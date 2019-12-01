package net.kiss.starter.graphql.dsl.common

open class GraphQLQuery<T>(
  val parent: GraphQLType<T>
) {
  val fields = mutableMapOf<String, GraphQLObjectField<T, *>>()

  protected fun <F> addField(field: String, context: GraphQLObjectField<T, F>) {
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

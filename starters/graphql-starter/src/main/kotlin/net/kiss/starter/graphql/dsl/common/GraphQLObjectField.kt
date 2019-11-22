package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.GraphQLRequest

abstract class GraphQLObjectField<T, F>(
  val field: String,
  private val parent: GraphQLQuery<T>
) {
  lateinit var fetcher: (GraphQLRequest) -> F
    get
    protected set
}

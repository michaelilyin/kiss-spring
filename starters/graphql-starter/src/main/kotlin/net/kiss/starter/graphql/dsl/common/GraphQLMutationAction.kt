package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.GraphQLRequest

open class GraphQLMutationAction<T, F>(
  val action: String,
  private val parent: GraphQLMutation<T>
) {
  lateinit var fetcher: suspend (GraphQLRequest) -> F
    get
    protected set
}

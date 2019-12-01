package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationResponse
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

open class GraphQLFederation<T>(
  protected val parent: GraphQLType<T>
) {

  lateinit var resolver: suspend (FederationRequest) -> FederationResponse<T>
    get
    protected set
}

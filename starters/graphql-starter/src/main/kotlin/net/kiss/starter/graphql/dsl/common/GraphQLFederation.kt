package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationResponse
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

open class GraphQLFederation<K, T>(
  protected val parent: GraphQLType<T>
) {

  lateinit var resolver: suspend (FederationRequest<K>) -> FederationResponse<K, T>
    get
    private set

  fun addResolver(resolver: suspend (FederationRequest<K>) -> FederationResponse<K, T>) {
    this.resolver = resolver
  }
}

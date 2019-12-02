package net.kiss.starter.graphql.dsl.federation

import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationResponse
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType

@GraphQLMarker
class LocalFederation<K, T>(parent: GraphQLLocalType<T>): GraphQLFederation<K, T>(parent) {
  fun resolve(resolve: suspend (FederationRequest<K>) -> FederationResponse<K, T>) {
    addResolver(resolve)
  }
}

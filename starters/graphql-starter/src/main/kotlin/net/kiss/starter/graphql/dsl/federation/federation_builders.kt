package net.kiss.starter.graphql.dsl.federation

import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType

@GraphQLMarker
class LocalFederation<T>(parent: GraphQLLocalType<T>): GraphQLFederation<T>(parent) {
  fun <Q : Any> resolve(resolve: (FederationRequest<Q>) -> List<T>) {
    resolver = resolve
  }
}

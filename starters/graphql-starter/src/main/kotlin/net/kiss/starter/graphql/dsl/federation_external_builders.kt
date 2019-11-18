package net.kiss.starter.graphql.dsl

@GraphQLMarker
class ForeignFederation<T>(parent: GraphQLForeignType<T>) {
  fun accept(resolve: (FederationRequest<Any>) -> List<T>) {

  }
}

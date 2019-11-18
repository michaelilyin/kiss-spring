package net.kiss.starter.graphql.dsl

@GraphQLMarker
class Federation<T>(parent: GraphQLType<T>) {
  fun resolve(resolve: (FederationRequest<Any>) -> List<T>) {

  }
}

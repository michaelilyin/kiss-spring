package net.kiss.starter.graphql.dsl

@GraphQLMarker
class Action<T, F>(field: String, parent: Mutation<T>) {
  fun execute(resolve: (GraphQLRequest) -> F) {

  }
}

@GraphQLMarker
class Mutation<T>(parent: GraphQLType<T>) {
//  fun <A> federate(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }

  fun <R> nestedMutationContext(field: String, resolve: (GraphQLRequest) -> R) {

  }

  @ActionKeyword
  fun <F> action(field: String, init: Action<T, F>.() -> Unit) {
    val context = Action<T, F>(field, this)
    context.init()
  }
}

package net.kiss.starter.graphql.dsl

@GraphQLMarker
class LocalAction<T, F>(field: String, parent: ForeignMutation<T>) {
  fun execute(resolve: (GraphQLRequest) -> F) {

  }
}

@GraphQLMarker
class ForeignMutation<T>(parent: GraphQLForeignType<T>) {
//  fun <A> federate(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }
//
//  fun <R> nestedMutation(field: String, resolve: (GraphQLRequest) -> R) {
// TODO: maybe needed
//  }

  @ActionKeyword
  fun <F> localAction(field: String, init: LocalAction<T, F>.() -> Unit) {
    val context = LocalAction<T, F>(field, this)
    context.init()
  }
}

package net.kiss.starter.graphql.dsl

@GraphQLMarker
class LocalField<T, F>(field: String, parent: ForeignQuery<T>) {
  fun fetch(resolve: (GraphQLRequest) -> F) {

  }
}

@GraphQLMarker
class ForeignQuery<T>(parent: GraphQLForeignType<T>) {
//  fun <A> federateForeign(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }

  @FieldKeyword
  fun <F> localField(field: String, init: LocalField<T, F>.() -> Unit) {
    val context = LocalField<T, F>(field, this)
    context.init()
  }
}

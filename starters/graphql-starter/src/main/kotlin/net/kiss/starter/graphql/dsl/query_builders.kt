package net.kiss.starter.graphql.dsl

@GraphQLMarker
class Field<T, F>(
  private val field: String,
  private val parent: Query<T>
) {
  fun fetch(resolve: (GraphQLRequest) -> F) {
    parent.setFetcher(field, resolve)
  }
}

@GraphQLMarker
class ForeignField<T, F>(field: String, parent: Query<T>) {
  fun buildFederationRequest(resolve: (GraphQLRequest) -> F) {

  }
}

@GraphQLMarker
class Query<T>(
  private val parent: GraphQLType<T>
) {
  @FieldKeyword
  fun <F> field(field: String, init: Field<T, F>.() -> Unit) {
    val context = Field<T, F>(field, this)
    context.init()
  }

  @FieldKeyword
  fun <F> foreignField(field: String, init: ForeignField<T, F>.() -> Unit) {
    val context = ForeignField<T, F>(field, this)
    context.init()
  }

  internal fun <F> setFetcher(field: String, resolve: (GraphQLRequest) -> F) {
    parent.setQueryFetcher(field, resolve);
  }
}

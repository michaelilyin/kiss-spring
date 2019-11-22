package net.kiss.starter.graphql.dsl.query

import net.kiss.starter.graphql.dsl.FieldKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.common.GraphQLQuery
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLForeignType

@GraphQLMarker
class LocalField<T, F>(
  field: String,
  parent: ForeignQuery<T>
) : GraphQLObjectField<T, F>(field, parent) {
  fun fetch(resolve: (GraphQLRequest) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class ForeignQuery<T>(parent: GraphQLForeignType<T>) : GraphQLQuery<T>(parent) {
//  fun <A> federateForeign(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }

  @FieldKeyword
  fun <F> localField(field: String, init: LocalField<T, F>.() -> Unit) {
    val context = LocalField<T, F>(field, this)
    context.init()

    addField(field, context)
  }
}

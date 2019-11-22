package net.kiss.starter.graphql.dsl.query

import net.kiss.starter.graphql.dsl.FieldKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.common.GraphQLQuery
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType

@GraphQLMarker
class Field<T, F>(
  field: String,
  parent: LocalQuery<T>
) : GraphQLObjectField<T, F>(field, parent) {
  fun fetch(resolve: (GraphQLRequest) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class ForeignField<T, F>(
  field: String,
  parent: LocalQuery<T>
) : GraphQLObjectField<T, F>(field, parent) {
  fun buildFederationRequest(resolve: (GraphQLRequest) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class LocalQuery<T>(
  parent: GraphQLLocalType<T>
) : GraphQLQuery<T>(parent) {
  @FieldKeyword
  fun <F> field(field: String, init: Field<T, F>.() -> Unit) {
    val context = Field<T, F>(field, this)
    context.init()

    addField(field, context)
  }

  @FieldKeyword
  fun <F> foreignField(field: String, init: ForeignField<T, F>.() -> Unit) {
    val context = ForeignField<T, F>(field, this)
    context.init()

    addField(field, context)
  }
}

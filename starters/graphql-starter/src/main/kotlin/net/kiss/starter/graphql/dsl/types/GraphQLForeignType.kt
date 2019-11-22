package net.kiss.starter.graphql.dsl.types

import net.kiss.starter.graphql.dsl.GraphQL
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.QueryKeyword
import net.kiss.starter.graphql.dsl.common.GraphQLType
import net.kiss.starter.graphql.dsl.federation.ForeignFederation
import net.kiss.starter.graphql.dsl.mutation.ForeignMutation
import net.kiss.starter.graphql.dsl.query.ForeignQuery

@GraphQLMarker
class GraphQLForeignType<T>(
  typename: String,
  parent: GraphQL
) : GraphQLType<T>(typename, parent) {
  @QueryKeyword
  infix fun query(init: ForeignQuery<T>.() -> Unit) {
    val context = ForeignQuery(this)
    context.init()

    addQuery(context)
  }

  @QueryKeyword
  infix fun mutation(init: ForeignMutation<T>.() -> Unit) {
    val context = ForeignMutation<T>(this)
    context.init()

    addMutation(context)
  }

  @QueryKeyword
  infix fun federate(init: ForeignFederation<T>.() -> Unit) {
    val context = ForeignFederation(this)
    context.init()

    addFederation(context)
  }
}


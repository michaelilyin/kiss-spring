package net.kiss.starter.graphql.dsl.types

import net.kiss.starter.graphql.dsl.GraphQL
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.QueryKeyword
import net.kiss.starter.graphql.dsl.common.GraphQLType
import net.kiss.starter.graphql.dsl.federation.LocalFederation
import net.kiss.starter.graphql.dsl.mutation.LocalMutation
import net.kiss.starter.graphql.dsl.query.LocalQuery

@GraphQLMarker
class GraphQLLocalType<T>(
  typename: String,
  parent: GraphQL
) : GraphQLType<T>(typename, parent) {

  @QueryKeyword
  infix fun query(init: LocalQuery<T>.() -> Unit) {
    val context = LocalQuery(this)
    context.init()

    addQuery(context)
  }

  @QueryKeyword
  infix fun mutation(init: LocalMutation<T>.() -> Unit) {
    val context = LocalMutation(this)
    context.init()

    addMutation(context)
  }

  @QueryKeyword
  infix fun <K> federate(init: LocalFederation<K, T>.() -> Unit) {
    val context = LocalFederation<K, T>(this)
    context.init()

    addFederation(context)
  }
}

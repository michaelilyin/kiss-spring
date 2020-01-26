package net.kiss.starter.graphql.dsl.types

import net.kiss.starter.graphql.dsl.GraphQL
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.QueryKeyword
import net.kiss.starter.graphql.dsl.common.GraphQLType
import net.kiss.starter.graphql.dsl.federation.LocalFederation
import net.kiss.starter.graphql.dsl.mutation.LocalMutation
import net.kiss.starter.graphql.dsl.query.LocalQuery
import kotlin.reflect.KClass

@GraphQLMarker
class GraphQLLocalType<T: Any>(
  typename: String,
  type: KClass<T>,
  parent: GraphQL
) : GraphQLType<T>(typename, type, parent) {

  @QueryKeyword
  infix fun query(init: LocalQuery<T>.() -> Unit) {
    val context = LocalQuery(type, this)
    context.init()

    addQuery(context)
  }

  @QueryKeyword
  infix fun mutation(init: LocalMutation<T>.() -> Unit) {
    val context = LocalMutation(type, this)
    context.init()

    addMutation(context)
  }

  @QueryKeyword
  inline fun <reified K: Any> federate(noinline init: LocalFederation<K, T>.() -> Unit) {
    federate(K::class, init)
  }

  @QueryKeyword
  fun <K : Any> federate(keyType: KClass<K>, init: LocalFederation<K, T>.() -> Unit) {
    val context = LocalFederation(keyType, this)
    context.init()

    addFederation(context)
  }
}

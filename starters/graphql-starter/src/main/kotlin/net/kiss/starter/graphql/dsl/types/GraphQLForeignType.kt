package net.kiss.starter.graphql.dsl.types

import net.kiss.starter.graphql.dsl.GraphQL
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.QueryKeyword
import net.kiss.starter.graphql.dsl.common.GraphQLType
import net.kiss.starter.graphql.dsl.federation.ForeignFederation
import net.kiss.starter.graphql.dsl.mutation.ForeignMutation
import net.kiss.starter.graphql.dsl.query.ForeignQuery
import kotlin.reflect.KClass

@GraphQLMarker
class GraphQLForeignType<T: Any>(
  typename: String,
  type: KClass<T>,
  parent: GraphQL
) : GraphQLType<T>(typename, type, parent) {
  @QueryKeyword
  infix fun query(init: ForeignQuery<T>.() -> Unit) {
    val context = ForeignQuery(type,this)
    context.init()

    addQuery(context)
  }

  @QueryKeyword
  infix fun mutation(init: ForeignMutation<T>.() -> Unit) {
    val context = ForeignMutation<T>(type, this)
    context.init()

    addMutation(context)
  }

  @QueryKeyword
  inline fun <reified K: Any> federate(noinline init: ForeignFederation<K, T>.() -> Unit) {
    federate(K::class, init)
  }

  @QueryKeyword
  fun <K: Any> federate(keyType: KClass<K>, init: ForeignFederation<K, T>.() -> Unit) {
    val context = ForeignFederation(keyType, this)
    context.init()

    addFederation(context)
  }

  @QueryKeyword
  fun convertToFederationRequest() {
    federate<Map<String, Any>> {
      resolveAsIs()
    }
  }
}


package net.kiss.starter.graphql.dsl.federation

import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationResponse
import net.kiss.starter.graphql.dsl.types.GraphQLForeignType
import kotlin.reflect.KClass

@GraphQLMarker
class ForeignFederation<K: Any, T: Any>(
  keyType: KClass<K>,
  parent: GraphQLForeignType<T>
): GraphQLFederation<K, T>(keyType, parent) {
  fun resolve(resolve: suspend (FederationRequest<K>) -> FederationResponse<T>) {
    resolver = resolve
  }
}

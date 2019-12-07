package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationResponse
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import kotlin.reflect.KClass

open class GraphQLFederation<K : Any, T: Any>(
  val keyType: KClass<K>,
  protected val parent: GraphQLType<T>
) {

  lateinit var resolver: suspend (FederationRequest<K>) -> FederationResponse<T>
    get
    protected set

  fun addResolver(resolver: suspend (FederationRequest<K>) -> FederationResponse<T>) {
    this.resolver = resolver
  }
}

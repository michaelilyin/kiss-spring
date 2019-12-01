package net.kiss.starter.graphql.dsl.data

import graphql.schema.DataFetchingEnvironment
import net.kiss.starter.graphql.config.FederationRequestItem
import net.kiss.starter.graphql.config.FederationRequestResult

class FederationResponse<K, T>(
  val forRequest: FederationRequest<K>,
  val itemsMap: Map<K, T>
) {
  val items: List<T>
    get() {
      return forRequest
    }
}

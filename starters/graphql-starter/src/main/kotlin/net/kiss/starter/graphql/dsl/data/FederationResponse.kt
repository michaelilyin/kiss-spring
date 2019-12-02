package net.kiss.starter.graphql.dsl.data

import net.kiss.starter.graphql.config.FederationRequestResult

class FederationResponse<K, T>(
  val request: FederationRequest<K>,
  val itemsMap: Map<K, T>
) {
  val items: List<FederationRequestResult<T?>>
    get() {
      return request.items.asSequence()
        .map { FederationRequestResult(it.index, itemsMap[it.key]) }
        .toList()
    }
}

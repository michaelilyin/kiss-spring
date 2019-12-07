package net.kiss.starter.graphql.dsl.data

class FederationResponse<T>(
  val items: List<FederationRequestResult<T?>>
) {
}

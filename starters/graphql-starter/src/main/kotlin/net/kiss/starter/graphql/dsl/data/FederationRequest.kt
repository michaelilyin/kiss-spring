package net.kiss.starter.graphql.dsl.data

import graphql.schema.DataFetchingEnvironment
import net.kiss.starter.graphql.config.FederationRequestItem

class FederationRequest<T>(
  private val env: List<FederationRequestItem>
) {
  companion object {
    fun <T> from(env: List<FederationRequestItem>): FederationRequest<T> {
      return FederationRequest(env)
    }
  }
  fun <D> mapArgs(name: String): List<D> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

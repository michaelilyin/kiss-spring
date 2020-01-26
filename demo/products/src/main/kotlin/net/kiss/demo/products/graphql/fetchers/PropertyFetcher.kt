package net.kiss.demo.products.graphql.fetchers

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture

/**
 * @author ilyin
 */
interface PropertyFetcher<S, P> {
  suspend fun fetchProperty(env: DataFetchingEnvironment): P {
    return fetchProperty(env.getSource<S>(), env)
  }

  suspend fun fetchProperty(source: S, env: DataFetchingEnvironment): P
}

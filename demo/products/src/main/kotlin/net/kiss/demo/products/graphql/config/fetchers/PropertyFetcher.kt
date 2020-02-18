package net.kiss.demo.products.graphql.config.fetchers

import graphql.schema.DataFetchingEnvironment

/**
 * @author ilyin
 */
interface PropertyFetcher<S, P> {
  suspend fun fetchProperty(env: DataFetchingEnvironment): P {
    return fetchProperty(env.getSource<S>(), env)
  }

  suspend fun fetchProperty(source: S, env: DataFetchingEnvironment): P
}

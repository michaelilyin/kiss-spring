package net.kiss.starter.grqphql.fetcher

import graphql.schema.DataFetchingEnvironment

interface PropertyFetcher<S, P> {
  suspend fun fetchProperty(env: DataFetchingEnvironment): P {
    return fetchProperty(env.getSource<S>(), env)
  }

  suspend fun fetchProperty(source: S, env: DataFetchingEnvironment): P
}

package net.kiss.starter.grqphql.fetcher

import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture

interface PropertyFetcher<S, P> {
  fun fetchProperty(env: DataFetchingEnvironment): CompletableFuture<P> {
    return fetchProperty(env.getSource<S>(), env)
  }

  fun fetchProperty(source: S, env: DataFetchingEnvironment): CompletableFuture<P>
}

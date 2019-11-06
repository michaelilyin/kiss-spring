package net.kiss.starter.graphql.builder

import graphql.schema.DataFetchingEnvironment


class FetcherBuilder<T>(
  val type: String
) {
  private val fetchers = mutableListOf<FetcherInfo<*>>()

  fun fetch(field: String, fetcher: suspend (env: DataFetchingEnvironment) -> T) {
    fetchers.add(FetcherInfo (
      field = field,
      fetcher = fetcher
    ))
  }

  fun build() = FetchersInfo(
    type = type,
    fetchers = fetchers
  )
}

data class FetcherInfo<T>(
  val field: String,
  val fetcher: suspend (env: DataFetchingEnvironment) -> T
)

data class FetchersInfo(
  val type: String,
  val fetchers: List<FetcherInfo<*>>
)

inline fun <R> rootFetcher(init: FetcherBuilder<R>.() -> Unit): FetchersInfo {
  val fetcher = FetcherBuilder<R>("Query")
  fetcher.init()
  return fetcher.build()
}

inline fun <reified T : Any, R> fetcher(init: FetcherBuilder<R>.() -> Unit): FetchersInfo {
  val name = T::class.simpleName ?: throw IllegalArgumentException("Fetch class should be explicit")
  val fetcher = FetcherBuilder<R>(name)
  fetcher.init()
  return fetcher.build()
}

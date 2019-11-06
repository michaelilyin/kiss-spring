package net.kiss.starter.graphql.builder

import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KClass


class FetcherBuilder(
  private val type: String
) {
  private val fetchers = mutableListOf<FetcherInfo<*>>()

  fun <T : Any> fetch(field: String, result: KClass<T>, fetcher: suspend (env: DataFetchingEnvironment) -> T) {
    addFetcher(field, fetcher)
  }

  fun <T : Any> fetchNullable(field: String, result: KClass<T>, fetcher: suspend (env: DataFetchingEnvironment) -> T?) {
    addFetcher(field, fetcher)
  }

  fun <T : Any> fetchList(field: String, result: KClass<T>, fetcher: suspend (env: DataFetchingEnvironment) -> List<T>) {
    addFetcher(field, fetcher)
  }

  private fun <T : Any> addFetcher(field: String, fetcher: suspend (env: DataFetchingEnvironment) -> T?) {
    fetchers.add(FetcherInfo(
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

inline fun rootFetcher(init: FetcherBuilder.() -> Unit): FetchersInfo {
  val fetcher = FetcherBuilder("Query")
  fetcher.init()
  return fetcher.build()
}

inline fun <reified T : Any> fetcher(init: FetcherBuilder.() -> Unit): FetchersInfo {
  val name = T::class.simpleName ?: throw IllegalArgumentException("Fetch class should be explicit")
  val fetcher = FetcherBuilder(name)
  fetcher.init()
  return fetcher.build()
}

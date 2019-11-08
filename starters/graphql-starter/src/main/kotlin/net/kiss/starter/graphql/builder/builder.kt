package net.kiss.starter.graphql.builder

import graphql.schema.DataFetchingEnvironment

data class FieldFetcher<T>(
  val field: String,
  val fetcher: suspend (env: DataFetchingEnvironment) -> T
)

data class TypeFetchers(
  val type: String,
  val fieldFetchers: List<FieldFetcher<*>>
)

data class FetchersGroup(
  val typeFetchers: Map<String, List<TypeFetchers>>
)

class FieldFetcherBuilder<T>(
  private val field: String
) {

  private lateinit var fetcher: suspend (env: DataFetchingEnvironment) -> T

  fun returning(fetcher: suspend (env: DataFetchingEnvironment) -> T) {
    this.fetcher = fetcher
  }

  fun build(): FieldFetcher<T> {
    return FieldFetcher(field, fetcher)
  }
}

class TypeFetchersBuilder(
  private val type: String
) {
  private val fieldFetchers = mutableListOf<FieldFetcher<*>>()

  fun <T : Any?> fetch(field: String, init: FieldFetcherBuilder<T>.() -> Unit) {
    val fieldFetcherBuilder = FieldFetcherBuilder<T>(field)
    fieldFetcherBuilder.init()
    buildFieldFetcher(fieldFetcherBuilder)
  }

  private fun <T : Any?> buildFieldFetcher(fieldFetcherBuilder: FieldFetcherBuilder<T>) {
    val fieldFetcher = fieldFetcherBuilder.build()
    fieldFetchers.add(fieldFetcher)
  }

  fun build() = TypeFetchers(
    type = type,
    fieldFetchers = fieldFetchers.toList()
  )
}

class FetchersGroupBuilder {
  private val typeBuilders = mutableMapOf<String, MutableList<TypeFetchers>>()

  inline fun query(init: TypeFetchersBuilder.() -> Unit) {
    val typeFetchersBuilder = TypeFetchersBuilder("Query")
    typeFetchersBuilder.init()
    buildTypeFetcher(typeFetchersBuilder)
  }

  inline fun <reified T : Any> entity(init: TypeFetchersBuilder.() -> Unit) {
    val name = T::class.simpleName ?: throw IllegalArgumentException("Fetch class should be explicit")
    val typeFetchersBuilder = TypeFetchersBuilder(name)
    typeFetchersBuilder.init()
    buildTypeFetcher(typeFetchersBuilder)
  }

  fun buildTypeFetcher(typeFetchersBuilder: TypeFetchersBuilder) {
    val typeFetcher = typeFetchersBuilder.build()
    typeBuilders.computeIfAbsent(typeFetcher.type) { mutableListOf() }
      .add(typeFetcher)
  }

  fun build(): FetchersGroup {
    val typeFetchers = typeBuilders.values.asSequence()
      .flatMap { it.asSequence() }
      .groupBy { it.type }
    return FetchersGroup(typeFetchers)
  }
}

inline fun buildFetchers(init: FetchersGroupBuilder.() -> Unit): FetchersGroup {
  val fetchersGroupBuilder = FetchersGroupBuilder()
  fetchersGroupBuilder.init()
  return fetchersGroupBuilder.build()
}

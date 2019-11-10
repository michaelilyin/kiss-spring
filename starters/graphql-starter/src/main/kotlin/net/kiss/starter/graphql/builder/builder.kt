package net.kiss.starter.graphql.builder

import graphql.schema.DataFetchingEnvironment

data class FieldFetcher<T>(
  val field: String,
  val fetcher: suspend (env: DataFetchingEnvironment) -> T
)

data class TypeFetchers<E>(
  val type: String,
  val fieldFetchers: List<FieldFetcher<*>>,
  val resolver: (suspend (env: Map<String, Any>) -> E?)?
)

data class FetchersGroup(
  val typeFetchers: Map<String, List<TypeFetchers<*>>>
)

class FieldFetcherBuilder<T>(
  private val field: String
) {

  private lateinit var fetcher: suspend (env: DataFetchingEnvironment) -> T

  fun invoke(fetcher: suspend (env: DataFetchingEnvironment) -> T) {
    this.fetcher = fetcher
  }

  fun build(): FieldFetcher<T> {
    return FieldFetcher(field, fetcher)
  }
}

open class TypeFetchersBuilder<E>(
  private val type: String
) {
  private val fieldFetchers = mutableListOf<FieldFetcher<*>>()
  private var resolver: (suspend (env: Map<String, Any>) -> E?)? = null

  protected fun <T : Any?> buildFieldFetcher(fieldFetcherBuilder: FieldFetcherBuilder<T>) {
    val fieldFetcher = fieldFetcherBuilder.build()
    fieldFetchers.add(fieldFetcher)
  }

  fun resolve(fetcher: suspend (env: Map<String, Any>) -> E?) {
    resolver = fetcher
  }

  fun build() = TypeFetchers(
    type = type,
    fieldFetchers = fieldFetchers.toList(),
    resolver = resolver
  )
}

class TypeFetchersQueryBuilder<E>(type: String)
  : TypeFetchersBuilder<E>(type) {

  fun <T : Any?> fetch(field: String, init: FieldFetcherBuilder<T>.() -> Unit) {
    val fieldFetcherBuilder = FieldFetcherBuilder<T>(field)
    fieldFetcherBuilder.init()
    buildFieldFetcher(fieldFetcherBuilder)
  }
}

class TypeFetchersMutationBuilder<E>(type: String)
  : TypeFetchersBuilder<E>(type) {
  fun <T : Any?> mutate(field: String, init: FieldFetcherBuilder<T>.() -> Unit) {
    val fieldFetcherBuilder = FieldFetcherBuilder<T>(field)
    fieldFetcherBuilder.init()
    buildFieldFetcher(fieldFetcherBuilder)
  }
}

class FetchersGroupBuilder {
  private val typeBuilders = mutableMapOf<String, MutableList<TypeFetchers<*>>>()

  inline fun query(init: TypeFetchersQueryBuilder<Any>.() -> Unit) {
    val typeFetchersBuilder = TypeFetchersQueryBuilder<Any>("Query")
    typeFetchersBuilder.init()
    buildTypeFetcher(typeFetchersBuilder)
  }

  inline fun <reified T : Any> entity(init: TypeFetchersQueryBuilder<T>.() -> Unit) {
    val name = T::class.simpleName ?: throw IllegalArgumentException("Fetch class should be explicit")
    val typeFetchersBuilder = TypeFetchersQueryBuilder<T>(name)
    typeFetchersBuilder.init()
    buildTypeFetcher(typeFetchersBuilder)
  }

  fun buildTypeFetcher(typeFetchersBuilder: TypeFetchersBuilder<*>) {
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

  fun mutation(init: TypeFetchersMutationBuilder<Any>.() -> Unit) {
    val typeFetchersBuilder = TypeFetchersMutationBuilder<Any>("Mutation")
    typeFetchersBuilder.init()
    buildTypeFetcher(typeFetchersBuilder)
  }
}

inline fun buildFetchers(init: FetchersGroupBuilder.() -> Unit): FetchersGroup {
  val fetchersGroupBuilder = FetchersGroupBuilder()
  fetchersGroupBuilder.init()
  return fetchersGroupBuilder.build()
}

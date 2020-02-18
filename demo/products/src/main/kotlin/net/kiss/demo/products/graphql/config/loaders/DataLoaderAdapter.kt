package net.kiss.demo.products.graphql.config.loaders

/**
 * @author ilyin
 */
interface DataLoaderAdapter<A, R> {
  suspend fun load(args: List<A>): List<R>
}

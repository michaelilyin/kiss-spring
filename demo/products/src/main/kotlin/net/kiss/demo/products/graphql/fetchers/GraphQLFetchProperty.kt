package net.kiss.demo.products.graphql.fetchers

import graphql.schema.DataFetcher
import net.kiss.demo.products.graphql.fetchers.PropertyFetcher
import net.kiss.demo.products.model.ProductCategory
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

/**
 * @author ilyin
 */
annotation class GraphQLFetchProperty(
  val fetcher: KClass<*>
)

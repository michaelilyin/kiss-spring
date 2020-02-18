package net.kiss.demo.products.graphql.config.fetchers

import kotlin.reflect.KClass

/**
 * @author ilyin
 */
annotation class GraphQLFetchProperty(
  val fetcher: KClass<*>
)

package net.kiss.demo.cart.graphql.fetcher

import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.future.await
import mu.KotlinLogging
import net.kiss.demo.cart.graphql.loader.Loader
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.Product
import net.kiss.starter.grqphql.fetcher.GraphQLFetcher
import net.kiss.starter.grqphql.fetcher.PropertyFetcher
import java.util.concurrent.CompletableFuture

@GraphQLFetcher
class CartProductsFetcher : PropertyFetcher<Cart, List<Product>> {

  private val logger = KotlinLogging.logger { }

  override fun fetchProperty(source: Cart, env: DataFetchingEnvironment): CompletableFuture<List<Product>> {
    logger.info { "Fetch products for cart ${source.id}" }

    val loader = env.getDataLoader<String, List<Product>>(Loader.PRODUCTS_BY_CART)

    logger.info { "Loader $loader" }

    return loader
      .load(source.id)
      .whenComplete { prod, _ ->
        logger.info { "Loaded products $prod" }
        source.products = prod
      }
  }
}

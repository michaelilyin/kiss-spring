package net.kiss.demo.cart.graphql.loader

import mu.KotlinLogging
import net.kiss.demo.cart.model.external.Product
import net.kiss.starter.grqphql.loader.GraphQLLoader
import net.kiss.starter.grqphql.loader.LoaderAdapter

@GraphQLLoader(Loader.PRODUCTS_BY_CART)
class ProductsByCartIdLoader : LoaderAdapter<String, List<Product>> {
  private val logger = KotlinLogging.logger {  }

  override suspend fun load(args: List<String>): List<List<Product>> {
    logger.info { "Load products by cart ids $args" }
    return args.map {
      listOf(
        Product("1-${it}"),
        Product("2-${it}")
      )
    }
  }
}

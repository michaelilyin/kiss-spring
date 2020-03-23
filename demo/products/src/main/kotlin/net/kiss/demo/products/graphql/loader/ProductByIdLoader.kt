package net.kiss.demo.products.graphql.loader

import mu.KotlinLogging
import net.kiss.demo.products.model.Product
import net.kiss.starter.grqphql.loader.GraphQLLoader
import net.kiss.starter.grqphql.loader.LoaderAdapter

@GraphQLLoader(Loader.PRODUCT_BY_ID)
class ProductByIdLoader : LoaderAdapter<String, Product> {
  private val logger = KotlinLogging.logger {  }

  override suspend fun load(args: List<String>): List<Product> {
    logger.info { "Load products by ids, $args" }

    return args.map {
      Product(
        id = it,
        name = "${it}-name",
        description = "${it}-description",
        categoryId = "123"
      )
    }
  }
}

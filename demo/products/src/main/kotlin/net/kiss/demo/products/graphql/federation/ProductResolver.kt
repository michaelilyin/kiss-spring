package net.kiss.demo.products.graphql.federation

import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.future.await
import mu.KLogging
import mu.KotlinLogging
import net.kiss.demo.products.graphql.loader.Loader
import net.kiss.demo.products.model.Product
import net.kiss.starter.grqphql.federation.FederationResolver
import org.springframework.stereotype.Component

@Component
class ProductResolver : FederationResolver<Product> {
  private val logger = KotlinLogging.logger {  }
  override val type = Product::class

  override suspend fun resolve(environment: DataFetchingEnvironment, representations: List<Map<String, Any>>): List<Product?> {
    logger.info { "Resolve products for federation, $representations" }

    val ids = representations.map { it["id"] as String }

    val future = environment.getDataLoader<String, Product>(Loader.PRODUCT_BY_ID).loadMany(ids)

    return future.await()
  }
}

package net.kiss.demo.products.graphql.loaders

import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import mu.KotlinLogging
import net.kiss.demo.products.model.ProductCategory
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.CompletableFuture

/**
 * @author ilyin
 */
@Configuration
class DataLoaderConfiguration {

  private val logger = KotlinLogging.logger {  }

  @Bean
  fun dataLoaderRegistryFactory(): DataLoaderRegistryFactory {
    val productLoader = object : DataLoaderAdapter<String, ProductCategory> {
      override suspend fun load(args: List<String>): List<ProductCategory> {
        return args.map { id ->
          ProductCategory(
            id = id,
            name = "$id name",
            parentId = null
          )
        }
      }

    }

    logger.info { "Call data loader factory" }
    return object : DataLoaderRegistryFactory {
      override fun generate(): DataLoaderRegistry {
        logger.info { "Call factory generate (create new registry)" }
        val registry = DataLoaderRegistry()
        val companyLoader = DataLoader<String, ProductCategory> { ids ->
          logger.info { "Call data loader" }
          GlobalScope.async {
            productLoader.load(ids)
          }.asCompletableFuture()
        }
        registry.register("ProductCategory", companyLoader)
        return registry
      }
    }
  }
}

package net.kiss.demo.products.graphql.product

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.spring.operations.Query
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.future.await
import net.kiss.demo.products.graphql.config.fetchers.PropertyFetcher
import net.kiss.demo.products.model.Product
import net.kiss.demo.products.model.ProductCategory
import org.springframework.stereotype.Component

/**
 * @author ilyin
 */
@Component
class ProductQuery : Query {
  suspend fun product(@GraphQLID id: String): Product? {
    val product = Product(
      id = id,
      name = "Some Product",
      description = "Some description",
      categoryId = 1
    )
    val cat = product.category
    val catId = product.categoryId
    return product
  }
}

@Component
class ProductCategoryFetcher :
  PropertyFetcher<Product, ProductCategory> {
  override suspend fun fetchProperty(source: Product, env: DataFetchingEnvironment): ProductCategory {
    return env.getDataLoader<String, ProductCategory>("ProductCategory")
      .load(source.id).await()
  }
}

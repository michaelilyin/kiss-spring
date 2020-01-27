package net.kiss.demo.products.graphql.fetcher

import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.future.await
import net.kiss.demo.products.graphql.loader.Loader
import net.kiss.demo.products.model.Product
import net.kiss.demo.products.model.ProductCategory
import net.kiss.starter.grqphql.fetcher.GraphQLFetcher
import net.kiss.starter.grqphql.fetcher.PropertyFetcher

@GraphQLFetcher
class ProductCategoryFetcher : PropertyFetcher<Product, ProductCategory> {
  override suspend fun fetchProperty(source: Product, env: DataFetchingEnvironment): ProductCategory {
    return env.getDataLoader<String, ProductCategory>(Loader.CATEGORY_BY_ID)
      .load(source.categoryId)
      .await()
      .also {
        source.category = it
      }
  }
}

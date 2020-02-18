package net.kiss.demo.products.model

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fasterxml.jackson.annotation.JsonIgnore
import net.kiss.demo.products.graphql.config.fetchers.GraphQLFetchProperty
import net.kiss.demo.products.graphql.product.ProductCategoryFetcher

data class Product(
  @GraphQLID
  val id: String,
  val name: String,
  val description: String,

  @GraphQLIgnore
  val categoryId: Long
) {
  @JsonIgnore
  @Suppress("unused")
  @GraphQLFetchProperty(ProductCategoryFetcher::class)
  lateinit var category: ProductCategory
}

package net.kiss.demo.products.model

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fasterxml.jackson.annotation.JsonIgnore
import net.kiss.demo.products.graphql.fetchers.GraphQLFetchProperty
import net.kiss.demo.products.graphql.product.ProductCategoryFether

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
  @GraphQLFetchProperty(ProductCategoryFether::class)
  lateinit var category: ProductCategory
}

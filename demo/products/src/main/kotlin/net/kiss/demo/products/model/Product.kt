package net.kiss.demo.products.model

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.federation.directives.FieldSet
import com.expediagroup.graphql.federation.directives.KeyDirective
import com.fasterxml.jackson.annotation.JsonIgnore
import net.kiss.demo.products.graphql.fetcher.ProductCategoryFetcher
import net.kiss.starter.grqphql.fetcher.GraphQLFetchProperty

@KeyDirective(fields = FieldSet("id"))
data class Product(
  @GraphQLID
  val id: String,
  val name: String,
  val description: String,

  @GraphQLIgnore
  val categoryId: String
) {
  @JsonIgnore
  @Suppress("unused")
  @GraphQLFetchProperty(ProductCategoryFetcher::class)
  lateinit var category: ProductCategory
}

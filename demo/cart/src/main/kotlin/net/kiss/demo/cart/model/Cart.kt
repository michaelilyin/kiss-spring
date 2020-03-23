package net.kiss.demo.cart.model

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fasterxml.jackson.annotation.JsonIgnore
import net.kiss.demo.cart.entity.CartEntity
import net.kiss.demo.cart.graphql.fetcher.CartProductsFetcher
import net.kiss.demo.cart.model.external.Product
import net.kiss.starter.grqphql.fetcher.GraphQLFetchProperty

data class Cart(
  @GraphQLID
  val id: String,

  @GraphQLIgnore
  val userId: String
) {
  @JsonIgnore
  @Suppress("unused")
  @GraphQLFetchProperty(fetcher = CartProductsFetcher::class)
  lateinit var products: List<Product>
}

fun CartEntity.toModel() = Cart(
  id = id!!,
  userId = userId
)

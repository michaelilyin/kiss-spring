package net.kiss.demo.cart.model

import com.expediagroup.graphql.annotations.GraphQLID
import net.kiss.demo.cart.entity.CartEntity

data class CartCreateInput(
  @GraphQLID
  val userId: String
)

fun CartCreateInput.toEntity() = CartEntity(
  id = null,
  userId = userId
)

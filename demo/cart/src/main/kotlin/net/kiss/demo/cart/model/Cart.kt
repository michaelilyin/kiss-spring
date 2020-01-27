package net.kiss.demo.cart.model

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.annotations.GraphQLIgnore
import net.kiss.demo.cart.entity.CartEntity

data class Cart(
  @GraphQLID
  val id: String,

  @GraphQLIgnore
  val userId: String
) {
}

fun CartEntity.toModel() = Cart(
  id = id!!,
  userId = userId
)

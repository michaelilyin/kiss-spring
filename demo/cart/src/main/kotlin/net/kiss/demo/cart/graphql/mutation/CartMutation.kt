package net.kiss.demo.cart.graphql.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.CartCreateInput
import net.kiss.demo.cart.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CartMutation @Autowired constructor(
  private val cartService: CartService
) : Mutation {
  suspend fun createCart(cart: CartCreateInput): Cart {
    return cartService.createCartForUser(cart)
  }
}

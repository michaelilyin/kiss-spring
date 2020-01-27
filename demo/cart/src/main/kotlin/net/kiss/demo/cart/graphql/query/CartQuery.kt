package net.kiss.demo.cart.graphql.query

import com.expediagroup.graphql.annotations.GraphQLID
import com.expediagroup.graphql.spring.operations.Query
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CartQuery @Autowired constructor(
  private val cartService: CartService
): Query {
  suspend fun cart(@GraphQLID id: String): Cart? {
    return cartService.findCartById(id)
  }
}

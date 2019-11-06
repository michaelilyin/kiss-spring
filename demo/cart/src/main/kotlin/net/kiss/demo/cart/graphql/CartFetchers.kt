package net.kiss.demo.cart.graphql

import net.kiss.demo.cart.model.Cart
import net.kiss.starter.graphql.builder.rootFetcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CartFetchers {

  private val carts = listOf(
    Cart(1, 1),
    Cart(2, 2),
    Cart(3, 3)
  )

  private val cartById = carts.groupBy { it.id }

  @Bean
  fun cartFetcher() = rootFetcher {
    fetchNullable("cart", Cart::class) {
      val cartId = it.getArgument<String>("id").toLong()

      cartById[cartId]?.first()
    }
  }
}

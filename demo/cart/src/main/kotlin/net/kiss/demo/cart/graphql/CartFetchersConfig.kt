package net.kiss.demo.cart.graphql

import mu.KotlinLogging
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.User
import net.kiss.starter.graphql.builder.buildFetchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.IllegalArgumentException

@Configuration
class CartFetchersConfig {

  private val logger = KotlinLogging.logger {}

  private val carts = listOf(
    Cart(1, 1),
    Cart(2, 2),
    Cart(3, 3)
  )

  private val cartById = carts.groupBy { it.id }

  @Bean
  fun cartFetchers() = buildFetchers {
    query {
      fetch<Cart?>("cart") {
        returning {
          val cartId = it.getArgument<String>("id").toLong()

          cartById[cartId]?.first()
        }
      }
    }

    entity<Cart> {
      resolve { arg ->
        val id = arg["id"] as? String ?: throw IllegalArgumentException()
        cartById[id.toLong()]?.first()
      }
      fetch<User>("user") {
        returning {
          val cart = it.getSource<Cart>();
          User(cart.userId)
        }
      }
    }

    entity<User> {
      resolve {
        User(
          id = (it["id"] as String).toLong()
        )
      }
      fetch<Cart?>("cart") {
        returning {
          logger.info { "Resolve cart for user" }
          val user = it.getSource<User>()
          carts.first { it.userId == user.id }
        }
      }
    }
  }
}

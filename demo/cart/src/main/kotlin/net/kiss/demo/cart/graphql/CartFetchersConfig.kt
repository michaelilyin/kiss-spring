package net.kiss.demo.cart.graphql

import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutationContext
import net.kiss.demo.cart.service.CartService
import net.kiss.starter.graphql.builder.buildFetchers
import net.kiss.starter.graphql.builder.getIdArgAsLong
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CartFetchersConfig {

  @Bean
  fun cartFetchers(cartService: CartService) = buildFetchers {
    query {
      fetch<Cart?>("cart") {
        invoke { cartService.findCartById(it.getIdArgAsLong()) }
      }
    }

    entity<Cart> {
      resolve { cartService.findCartById(it.getIdArgAsLong()) }

      fetch<User>("user") {
        invoke {
          val cart = it.getSource<Cart>();
          User(cart.userId)
        }
      }
    }

    entity<User> {
      resolve { User(it.getIdArgAsLong()) }

      fetch<Cart?>("cart") {
        invoke {
          val user = it.getSource<User>()
          cartService.findCartByUserId(user.id)
        }
      }
    }

    entity<UserMutationContext> {
      resolve { UserMutationContext(it.getIdArgAsLong()) }

      fetch<Cart>("createCart") {
        invoke {
          val user = it.getSource<UserMutationContext>()
          cartService.createCart(user.id)
        }
      }
    }
  }
}

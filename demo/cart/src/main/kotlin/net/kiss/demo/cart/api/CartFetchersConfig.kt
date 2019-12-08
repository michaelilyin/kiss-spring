package net.kiss.demo.cart.api

import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutation
import net.kiss.demo.cart.service.CartService
import net.kiss.starter.graphql.dsl.data.toFederationResponse
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.LongID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CartFetchersConfig {

  @Bean
  fun cartFetchers(cartService: CartService) = graphql {
    query {
      field<LongID, Cart?>("cart") {
        fetch {
          cartService.findCartById(it.arg.id)
        }
      }
    }

    type<Cart> {
      federate<LongID> {
        resolve {
          val carts = cartService.getCartsByIds(it.keys.map { it.id })
          carts.toFederationResponse(it) { LongID(it.id) }
        }
      }
    }

    foreignType<User> {
      query {
        localField<Unit, Cart?>("cart") {
          fetch {
            cartService.findCartByUserId(it.source.id)
          }
        }
      }
    }

    foreignType<UserMutation> {
      mutation {
        localAction<Unit, Cart>("createCart") {
          execute {
            cartService.createCart(it.source.id)
          }
        }
      }
    }
  }
}

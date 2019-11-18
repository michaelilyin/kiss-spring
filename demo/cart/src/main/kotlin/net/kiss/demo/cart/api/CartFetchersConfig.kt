package net.kiss.demo.cart.api

import net.kiss.demo.cart.handler.CartHandler
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutation
import net.kiss.demo.cart.service.CartService
import net.kiss.starter.graphql.builder.buildFetchers
import net.kiss.starter.graphql.builder.getIdArgAsLong
import net.kiss.starter.graphql.dsl.graphql
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CartFetchersConfig {

  @Bean
  fun cartFetchersDeprecated(cartService: CartService) = buildFetchers {
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

    entity<UserMutation> {
      resolve { UserMutation(it.getIdArgAsLong()) }

      fetch<Cart>("createCart") {
        invoke {
          val user = it.getSource<UserMutation>()
          cartService.createCart(user.id)
        }
      }
    }
  }

  @Bean
  fun cartFetchers(cartHandler: CartHandler) = graphql {
    query {
      field<Cart?>("cart") {
        fetch(cartHandler::findCart)
      }
    }

    type<Cart> {
      federate {
        resolve(cartHandler::resolveCarts)
      }
    }

    foreignType<User> {
      query {
        localField<Cart?>("cart") {
          fetch(cartHandler::findUserCart)
        }
      }
    }

    foreignType<UserMutation> {
      mutation {
        localAction<Cart>("createCart") {
          execute(cartHandler::createCart)
        }
      }
    }
  }
}

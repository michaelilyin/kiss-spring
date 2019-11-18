package net.kiss.demo.cart.api

import net.kiss.demo.cart.handler.UserHandler
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutation
import net.kiss.starter.graphql.dsl.graphql
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserFetchersConfig {
  @Bean
  fun userFetchers(userHandler: UserHandler) = graphql {
    type<Cart> {
      query {
        foreignField<User>("user") {
          buildFederationRequest(userHandler::stubUserForCart)
        }
      }
    }

    foreignType<User> {
      federate {
        accept(userHandler::stubUsers)
      }
    }

    foreignType<UserMutation> {
      federate {
        accept(userHandler::stubUserMutations)
      }
    }
  }
}

package net.kiss.demo.cart.api

import net.kiss.demo.cart.handler.UserHandler
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutation
import net.kiss.starter.graphql.dsl.data.toFederationResponse
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.LongID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserFetchersConfig {
  @Bean
  fun userFetchers() = graphql {
    type<Cart> {
      query {
        foreignField<Unit, LongID>("user") {
          buildFederationRequest {
            LongID(it.context.userId)
          }
        }
      }
    }

    foreignType<User> {
      federate<LongID> {
        resolve {
          it.keys.map { User(it.id) }.toFederationResponse(it) { LongID(it.id) }
        }
      }
    }

    foreignType<UserMutation> {
      federate<LongID> {
        resolve {
          it.keys.map { UserMutation(it.id) }.toFederationResponse(it) { LongID(it.id) }
        }
      }
    }
  }
}

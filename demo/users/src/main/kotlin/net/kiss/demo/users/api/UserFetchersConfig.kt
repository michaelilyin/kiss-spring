package net.kiss.demo.users.api

import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreate
import net.kiss.demo.users.service.UserService
import net.kiss.starter.graphql.dsl.data.toFederationResponse
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.LongID
import net.kiss.starter.graphql.model.SimpleInput
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserFetchersConfig {

  data class UserInput(
    override val input: UserCreate
  ) : SimpleInput<UserCreate>

  @Bean
  fun userFetchers(userService: UserService) = graphql {
    query {
      field<LongID, User?>("user") {
        fetch {
          userService.findUserById(it.arg.id)
        }
      }

      field<Unit, List<User>>("users") {
        fetch {
          userService.getUsers()
        }
      }
    }

    mutation {
      nestedMutationContext<LongID, User>("user") {
        userService.findUserById(it.arg.id) ?: throw IllegalArgumentException()
      }

      action<UserInput, User>("createUser") {
        execute {
          userService.createUser(it.arg.input)
        }
      }
    }

    type<User> {
      federate<LongID> {
        resolve {
          val users = userService.resolveById(it.keys.map { it.id })
          users.toFederationResponse(it) { LongID(it.id) }
        }
      }
    }
  }

}

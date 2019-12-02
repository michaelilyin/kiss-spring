package net.kiss.demo.users.api

import net.kiss.demo.users.handler.UserHandler
import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreate
import net.kiss.demo.users.service.RoleService
import net.kiss.demo.users.service.UserService
import net.kiss.starter.graphql.builder.buildFetchers
import net.kiss.starter.graphql.builder.getIdArgAsLong
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.LongID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserFetchersConfig {

  @Bean
  fun userFetchers(userHandler: UserHandler) = graphql {
    query {
      field<User?>("user") {
        fetch(userHandler::findUser)
      }

      field<List<User>>("users") {
        fetch(userHandler::getUsers)
      }
    }

    mutation {
      nestedMutationContext("user", userHandler::findUser)

      action<User>("createUser") {
        execute(userHandler::createUser)
      }
    }

    type<User> {
      federate<LongID> {
        resolve(userHandler::resolveUsers)
      }
    }
  }

}

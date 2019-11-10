package net.kiss.demo.users.graphql

import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreateInput
import net.kiss.demo.users.service.RoleService
import net.kiss.demo.users.service.UserService
import net.kiss.starter.graphql.builder.buildFetchers
import net.kiss.starter.graphql.builder.getIdArgAsLong
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserFetchersConfig {

  @Bean
  fun userFetchers(userService: UserService,
                   roleService: RoleService) = buildFetchers {
    query {
      fetch<User?>("user") {
        invoke { userService.findUserById(it.getIdArgAsLong()) }
      }

      fetch<List<User>>("users") {
        invoke { userService.getUsers() }
      }
    }

    mutation {
      mutate<User?>("user") {
        invoke { userService.findUserById(it.getIdArgAsLong()) }
      }
      mutate<User>("createUser") {
        invoke {
          val input = UserCreateInput(it.getArgument<Map<String, Any>>("user"))
          userService.createUser(input)
        }
      }
    }

    entity<User> {
      resolve { userService.findUserById(it.getIdArgAsLong()) }

      fetch<List<Role>>("roles") {
        invoke {
          val user = it.getSource<User>()
          roleService.getUserRoles(user.id)
        }
      }
    }
  }

}

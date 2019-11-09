package net.kiss.demo.users.graphql

import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.demo.users.service.RoleService
import net.kiss.demo.users.service.UserService
import net.kiss.starter.graphql.builder.buildFetchers
import net.kiss.starter.graphql.builder.getIdArgAsLong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserFetchersConfig {

  @Bean
  fun userFetchers(userService: UserService,
                   roleService: RoleService) = buildFetchers {
    query {
      fetch<User?>("user") {
        returning { userService.findUserById(it.getIdArgAsLong()) }
      }

      fetch<List<User>>("users") {
        returning { userService.getUsers() }
      }
    }

    entity<User> {
      resolve { userService.findUserById(it.getIdArgAsLong()) }

      fetch<List<Role>>("roles") {
        returning {
          val user = it.getSource<User>()
          roleService.getUserRoles(user.id)
        }
      }
    }
  }

}

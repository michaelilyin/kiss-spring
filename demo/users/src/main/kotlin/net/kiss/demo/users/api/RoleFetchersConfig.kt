package net.kiss.demo.users.api

import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.demo.users.service.RoleService
import net.kiss.starter.graphql.dsl.graphql
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleFetchersConfig {
  @Bean
  fun roleFetchers(roleService: RoleService) = graphql {
    type<User> {
      query {
        field<Unit, List<Role>>("roles") {
          fetch {
            roleService.getUserRoles(it.source.id)
          }
        }
      }
    }
  }
}

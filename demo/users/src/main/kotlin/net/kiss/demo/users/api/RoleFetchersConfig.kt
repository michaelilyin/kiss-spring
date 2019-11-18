package net.kiss.demo.users.api

import net.kiss.demo.users.handler.RoleHandler
import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.service.utils.findAnnotation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleFetchersConfig {
  @Bean
  fun roleFetchers(roleHandler: RoleHandler) = graphql {
    type<User> {
      query {
        field<List<Role>>("roles") {
          fetch(roleHandler::getUserRoles)
        }
      }
    }
  }
}

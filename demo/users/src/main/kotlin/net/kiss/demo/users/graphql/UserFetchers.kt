package net.kiss.demo.users.graphql

import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.starter.graphql.builder.FetcherInfo
import net.kiss.starter.graphql.builder.fetcher
import net.kiss.starter.graphql.builder.rootFetcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.IllegalArgumentException

@Configuration
class UserFetchers {
  val logger = KotlinLogging.logger {  }

  val users = listOf(
    User(1, "gandalf"),
    User(2, "frodo"),
    User(2, "aragorn")
  )

  val roles = mapOf(
    Pair(1L, listOf(Role(1, "WIZZARD"), Role(2, "STRANGER"))),
    Pair(2L, listOf(Role(3, "HALFING"), Role(4, "RING-BEARER"))),
    Pair(3L, listOf(Role(5, "HUMAN"), Role(6, "KING")))
  )

  @Bean
  fun usersFetcher() = rootFetcher {
    fetchList("users", User::class) {
      logger.info { "Fetch users" }
      users
    }
  }

  @Bean
  fun userRolesFetcher() = fetcher<User> {
    fetchList("roles", Role::class) {
      val user = it.getSource<User>()
      logger.info { "Fetch roles for user ${user.id}" }

      val userRoles = roles[user.id]
      userRoles ?: emptyList()
    }
  }
}

package net.kiss.demo.users.graphql

import mu.KotlinLogging
import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.starter.graphql.builder.buildFetchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.IllegalArgumentException

@Configuration
class UserFetchersConfig {
  val logger = KotlinLogging.logger {  }

  val users = listOf(
    User(1, "gandalf"),
    User(2, "frodo"),
    User(3, "aragorn")
  )

  val roles = mapOf(
    Pair(1L, listOf(Role(1, "WIZZARD"), Role(2, "STRANGER"))),
    Pair(2L, listOf(Role(3, "HALFING"), Role(4, "RING-BEARER"))),
    Pair(3L, listOf(Role(5, "HUMAN"), Role(6, "KING")))
  )

  @Bean
  fun userFetchers() = buildFetchers {
    query {
      fetch<User?>("user") {
        returning {
          val id = it.getArgument<String>("id").toLong()
          logger.info { "Fetch user by $id" }
          users.find { it.id == id }
        }
      }

      fetch<List<User>>("users") {
        returning {
          logger.info { "Fetch users" }
          users
        }
      }
    }

    entity<User> {
      resolve { arg ->
        val id = arg["id"] as? String ?: throw IllegalArgumentException()
        users.find { it.id == id.toLong() }
      }

      fetch<List<Role>>("roles") {
        returning {
          val user = it.getSource<User>()
          logger.info { "Fetch roles for user ${user.id}" }

          val userRoles = roles[user.id]
          userRoles ?: emptyList()
        }
      }
    }
  }

}

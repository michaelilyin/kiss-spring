package net.kiss.demo.users.service.impl

import mu.KotlinLogging
import net.kiss.demo.users.model.Role
import net.kiss.demo.users.model.User
import net.kiss.demo.users.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
  val logger = KotlinLogging.logger {  }

  val USERS = listOf(
    User(1, "gandalf"),
    User(2, "frodo"),
    User(3, "aragorn")
  )

  override fun findUserById(id: Long): User? {
    return USERS.find { it.id == id }
  }

  override fun getUsers(): List<User> {
    return USERS
  }
}

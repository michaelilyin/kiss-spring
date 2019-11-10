package net.kiss.demo.users.service.impl

import mu.KotlinLogging
import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreateInput
import net.kiss.demo.users.service.UserService
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class UserServiceImpl : UserService {
  val logger = KotlinLogging.logger {  }

  final val ID = AtomicLong(0)
  final val USERS = mutableListOf(
    User(ID.incrementAndGet(), "gandalf"),
    User(ID.incrementAndGet(), "frodo"),
    User(ID.incrementAndGet(), "aragorn")
  )

  override fun findUserById(id: Long): User? {
    logger.info { "Find user by id $id" }
    return USERS.find { it.id == id }
  }

  override fun createUser(input: UserCreateInput): User {
    logger.info { "Create new user from $input" }
    val user = User(
      id = ID.incrementAndGet(),
      username = input.username
    )
    USERS.add(user)
    return user
  }

  override fun getUsers(): List<User> {
    logger.info { "Get users" }
    return USERS
  }
}

package net.kiss.demo.users.service.impl

import mu.KotlinLogging
import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreate
import net.kiss.demo.users.model.toEntity
import net.kiss.demo.users.model.toModel
import net.kiss.demo.users.repository.UserRepository
import net.kiss.demo.users.service.UserService
import net.kiss.starter.service.utils.orNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
  private val userRepository: UserRepository
) : UserService {
  val logger = KotlinLogging.logger {  }

  override fun findUserById(id: Long): User? {
    val entity = userRepository.findById(id)
    return entity.map { it.toModel() }.orNull()
  }

  override fun createUser(input: UserCreate): User {
    val entity = input.toEntity()
    return userRepository.save(entity).toModel()
  }

  override fun resolveById(args: List<Long>): List<User> {
    return userRepository.findAllById(args).map { it.toModel() }
  }

  override fun getUsers(): List<User> {
    val users = userRepository.findAll()
    return users.map { it.toModel() }
  }
}

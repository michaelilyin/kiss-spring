package net.kiss.demo.users.service

import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreate

interface UserService {
  suspend fun findUserById(id: Long): User?
  suspend fun getUsers(): List<User>
  suspend fun createUser(input: UserCreate): User
  suspend fun resolveById(args: List<Long>): List<User>

}

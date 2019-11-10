package net.kiss.demo.users.service

import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreateInput

interface UserService {
  fun findUserById(id: Long): User?
  fun getUsers(): List<User>
  fun createUser(input: UserCreateInput): User

}

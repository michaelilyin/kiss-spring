package net.kiss.demo.users.service

import net.kiss.demo.users.model.User

interface UserService {
  fun findUserById(id: Long): User?
  fun getUsers(): List<User>

}

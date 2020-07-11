package net.kiss.demo.shopping.list.dto

import net.kiss.demo.shopping.list.entity.UserEntity
import java.util.*

data class UserView (
  val id: UUID,
  val username: String,
  val firstName: String,
  val lastName: String
)

fun UserEntity.toView() = UserView(
  id = id,
  username = username,
  firstName = firstName,
  lastName = lastName
)

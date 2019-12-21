package net.kiss.demo.auth.model.user

import net.kiss.demo.auth.entity.UserAccountEntity

data class UserAccount(
  val id: Long,
  val username: String,
  val enabled: Boolean,
  val firstName: String,
  val lastName: String?
) {
}

fun UserAccountEntity.toModel() = UserAccount(
  id = id!!,
  username = username,
  enabled = enabled,
  firstName = firstName,
  lastName = lastName
)

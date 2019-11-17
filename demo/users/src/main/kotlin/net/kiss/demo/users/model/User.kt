package net.kiss.demo.users.model

import net.kiss.demo.users.entity.UserEntity

data class User(
  val id: Long,
  val username: String
) {
}

fun User.toEntity() = UserEntity(
  id = id,
  username = username
)

fun UserEntity.toModel() = User(
  id = id!!,
  username = username
)

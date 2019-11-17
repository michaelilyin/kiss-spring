package net.kiss.demo.users.model

import net.kiss.demo.users.entity.UserEntity

data class UserCreate(
  val username: String
) {
  constructor(map: Map<String, Any>) : this(
    map["username"] as String
  ) {

  }
}

fun UserCreate.toEntity() = UserEntity(
  id = null,
  username = username
)

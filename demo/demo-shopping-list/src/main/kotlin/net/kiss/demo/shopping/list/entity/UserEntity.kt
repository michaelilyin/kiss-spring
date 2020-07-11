package net.kiss.demo.shopping.list.entity

import java.util.*

data class UserEntity (
  val id: UUID,
  val username: String,
  var firstName: String,
  var lastName: String
)

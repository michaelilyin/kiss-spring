package net.kiss.starter.service.auth.model

data class UserProfile(
  val id: Long,
  val username: String,
  val firstName: String,
  val lastName: String?
)

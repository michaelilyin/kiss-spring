package net.kiss.demo.auth.model.user

data class UserAccountCreate(
  val username: String,
  val password: String,
  val firstName: String,
  val lastName: String?
) {
}

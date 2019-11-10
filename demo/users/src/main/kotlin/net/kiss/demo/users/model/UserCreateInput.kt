package net.kiss.demo.users.model

data class UserCreateInput(
  val username: String
) {
  constructor(map: Map<String, Any>) : this(
    map["username"] as String
  ) {

  }
}

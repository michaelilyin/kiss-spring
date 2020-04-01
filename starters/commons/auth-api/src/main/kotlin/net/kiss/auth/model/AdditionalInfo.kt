package net.kiss.auth.model

class AdditionalInfo(source: Map<String, *>) {
  val id: Long = (source["id"] as Number).toLong()
  val username: String? = source["user_name"] as String
  val firstName: String = source["first_name"] as String
  val lastName: String? = source["last_name"] as String?
  val tracing: String? = source["tracing"] as String
  @Suppress("UNCHECKED_CAST")
  val roles: List<String> = source["roles"] as List<String>? ?: emptyList()

  constructor(id: Long, username: String, firstName: String, lastName: String?, tracing: String, roles: List<String>) : this(
    mapOf(
      "id" to id,
      "user_name" to username,
      "first_name" to firstName,
      "last_name" to lastName,
      "tracing" to tracing,
      "roles" to roles
    )
  )
}

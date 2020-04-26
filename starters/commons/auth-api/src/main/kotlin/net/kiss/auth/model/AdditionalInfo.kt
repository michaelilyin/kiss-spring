package net.kiss.auth.model

data class AdditionalInfo(
  val id: String,
  val username: String,
  val firstName: String,
  val lastName: String?,
  val tracing: String? = null,
  val roles: List<String> = emptyList()
) {
  @Suppress("UNCHECKED_CAST")
  constructor(
    source: Map<String, *>
  ) : this(
    id = source["id"] as String,
    username = source["user_name"] as String,
    firstName = source["first_name"] as String,
    lastName = source["last_name"] as String?,
    tracing = source["tracing"] as String?,
    roles = source["roles"] as List<String>? ?: emptyList()
  )
}

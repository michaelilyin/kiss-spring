package net.kiss.auth.model

import java.util.*

data class AdditionalInfo(
  val id: UUID,
  val username: String,
  val email: String,
  val firstName: String,
  val lastName: String?,
  val tracing: String? = null,
  val roles: List<String> = emptyList()
) {
  @Suppress("UNCHECKED_CAST")
  constructor(
    source: Map<String, *>
  ) : this(
    id = if (source["id"] is UUID) source["id"] as UUID else UUID.fromString(source["id"] as String),
    username = source["user_name"] as String,
    email = source["email"] as String,
    firstName = source["first_name"] as String,
    lastName = source["last_name"] as String?,
    tracing = source["tracing"] as String?,
    roles = source["roles"] as List<String>? ?: emptyList()
  )
}

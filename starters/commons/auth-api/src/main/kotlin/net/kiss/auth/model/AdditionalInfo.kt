package net.kiss.auth.model

class AdditionalInfo(val source: Map<String, *>) {
  val id: Long get() = (source["id"] as Number).toLong()
  val username: String? get() = source["user_name"] as String
  val firstName: String get() = source["first_name"] as String
  val lastName: String? get() = source["last_name"] as String?

  constructor(id: Long, username: String, firstName: String, lastName: String?) : this(
    mapOf(
      "id" to id,
      "user_name" to username,
      "first_name" to firstName,
      "last_name" to lastName
    )
  )
}

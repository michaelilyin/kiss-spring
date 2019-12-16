package net.kiss.auth.model

class AdditionalInfo(val source: Map<String, *>) {
  val id: Long get() = (source["id"] as Number).toLong()
  val firstName: String get() = source["first_name"] as String
  val lastName: String? get() = source["last_name"] as String?

  constructor(id: Long, firstName: String, lastName: String?) : this(
    mapOf(
      "id" to id,
      "first_name" to firstName,
      "last_name" to lastName
    )
  )
}

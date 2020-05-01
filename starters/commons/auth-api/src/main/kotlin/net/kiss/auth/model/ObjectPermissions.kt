package net.kiss.auth.model

import kotlinx.coroutines.GlobalScope
import java.io.Serializable
import java.util.*

interface ObjectPermissions {
  val type: String

  class Permission(
    private val obj: ObjectPermissions,
    private val check: suspend (id: Serializable, userId: UUID) -> Boolean
  ) {
    suspend fun check(id: Serializable, type: String, userId: UUID): Boolean {
      if (type != obj.type) {
        return false
      }
      return check(id, userId)
    }
  }
}

fun Serializable.longValue(): Long {
  if (this is Long) {
    return this
  }
  if (this is String) {
    return this.toLong()
  }
  throw UnsupportedOperationException(this.javaClass.name)
}

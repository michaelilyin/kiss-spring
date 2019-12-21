package net.kiss.starter.service.jpa.entity

abstract class Persistable<ID> {
  abstract val id: ID?

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Persistable<*>

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id?.hashCode() ?: 0
  }
}

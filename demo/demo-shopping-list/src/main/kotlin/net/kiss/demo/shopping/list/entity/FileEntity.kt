package net.kiss.demo.shopping.list.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("files")
data class FileEntity(
  @Id
  @Column("id")
  val key: UUID,
  val filename: String,
  val data: ByteArray
): Persistable<UUID> {

  override fun getId(): UUID? {
    return key
  }

  override fun isNew(): Boolean {
    return true
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FileEntity

    if (id != other.id) return false
    if (filename != other.filename) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + filename.hashCode()
    return result
  }
}

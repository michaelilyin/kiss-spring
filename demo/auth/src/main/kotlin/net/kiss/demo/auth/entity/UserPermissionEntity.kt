package net.kiss.demo.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("permissions")
data class UserPermissionEntity(
  @Id
  @Column("id")
  val id: Long?,

  @Column("code")
  val code: String,

  @Column("name")
  var name: String,

  @Column("description")
  var description: String
)

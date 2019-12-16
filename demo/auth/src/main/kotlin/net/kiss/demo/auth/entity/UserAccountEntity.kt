package net.kiss.demo.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserAccountEntity(
  @Id
  @Column("id")
  val id: Long?,

  @Column("username")
  val username: String,

  @Column("password")
  val password: String,

  @Column("enabled")
  val enabled: Boolean,

  @Column("first_name")
  val firstName: String,

  @Column("last_name")
  val lastName: String?
)

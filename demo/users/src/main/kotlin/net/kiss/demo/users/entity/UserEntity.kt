package net.kiss.demo.users.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserEntity(
  @Id
  @Column("id")
  val id: Long?,

  @Column("username")
  val username: String
)

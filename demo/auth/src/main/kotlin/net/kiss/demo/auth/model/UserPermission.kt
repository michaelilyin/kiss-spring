package net.kiss.demo.auth.model

import net.kiss.demo.auth.entity.UserPermissionEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("permissions")
data class UserPermission(
  val id: Long,
  val code: String,
  var name: String,
  var description: String
)

fun UserPermissionEntity.toModel() = UserPermission(
  id = id!!,
  code = code,
  name = name,
  description = description
)

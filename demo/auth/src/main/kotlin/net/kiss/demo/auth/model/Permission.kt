package net.kiss.demo.auth.model

import net.kiss.demo.auth.entity.PermissionEntity

data class UserPermission(
  val id: Long,
  val code: String,
  var name: String,
  var description: String
)

fun PermissionEntity.toModel() = UserPermission(
  id = id!!,
  code = code,
  name = name,
  description = description
)

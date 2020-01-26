package net.kiss.demo.auth.model.permission

import net.kiss.demo.auth.entity.PermissionEntity

data class Permission(
  val id: Long,
  val code: String,
  var name: String,
  var description: String
)

fun PermissionEntity.toModel() = Permission(
  id = id!!,
  code = code,
  name = name,
  description = description
)

package net.kiss.demo.auth.model.role

import net.kiss.demo.auth.entity.RoleEntity

data class Role(
  val id: Long,
  val code: String,
  var name: String,
  var description: String,
  val system: Boolean
) {
}

fun RoleEntity.toModel() = Role(
    id = id!!,
    code = code,
    name = name,
    description = description,
    system = system
)

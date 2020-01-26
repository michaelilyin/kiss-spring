package net.kiss.demo.auth.model.role

import net.kiss.demo.auth.entity.UserRoleGrantEntity

data class UserRole(
  val roleId: Long,
  val grantUserId: Long,
  val system: Boolean
) {
}

fun UserRoleGrantEntity.toModel() = UserRole(
    roleId = id.roleId,
    grantUserId = grantUserId,
    system = system
)

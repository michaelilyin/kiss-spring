package net.kiss.demo.auth.model.permission

import net.kiss.demo.auth.entity.RolePermissionGrantEntity

data class RolePermission(
  val permissionId: Long,
  val grantUserId: Long,
  val system: Boolean
) {

}

fun RolePermissionGrantEntity.toModel(): RolePermission =
  RolePermission(
    permissionId = id.permissionId,
    grantUserId = grantUserId,
    system = system
  )

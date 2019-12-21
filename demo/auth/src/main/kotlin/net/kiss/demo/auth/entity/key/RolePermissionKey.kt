package net.kiss.demo.auth.entity.key

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class RolePermissionKey(
  @Column(name = "role_id")
  val roleId: Long,
  @Column(name = "permission_id")
  val permissionId: Long
) : Serializable

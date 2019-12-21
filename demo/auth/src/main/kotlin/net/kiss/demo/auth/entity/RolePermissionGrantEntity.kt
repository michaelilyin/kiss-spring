package net.kiss.demo.auth.entity

import net.kiss.demo.auth.entity.key.RolePermissionKey
import net.kiss.starter.service.jpa.entity.Persistable
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "role_permissions")
data class RolePermissionGrantEntity(
  @EmbeddedId
  override val id: RolePermissionKey,

  @Column(name = "grant_user_id", updatable = false)
  val grantUserId: Long,

  @Column(name = "system", updatable = false)
  val system: Boolean
): Persistable<RolePermissionKey>()

package net.kiss.demo.auth.entity

import net.kiss.demo.auth.entity.key.UserRoleKey
import net.kiss.starter.service.jpa.entity.Persistable
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_roles")
data class UserRoleGrantEntity(
  @EmbeddedId
  override val id: UserRoleKey,

  @Column(name = "grant_user_id", updatable = false)
  val grantUserId: Long,

  @Column(name = "system", updatable = false)
  val system: Boolean
): Persistable<UserRoleKey>()

package net.kiss.demo.auth.entity.key

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class UserRoleKey(
  @Column(name = "user_id")
  val userId: Long,
  @Column(name = "role_id")
  val roleId: Long
) : Serializable

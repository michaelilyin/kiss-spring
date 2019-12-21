package net.kiss.demo.auth.entity

import net.kiss.starter.service.jpa.entity.Persistable
import javax.persistence.*

@Entity
@Table(name = "default_roles")
data class DefaultRoleEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  override val id: Long?,

  @ManyToOne
  @JoinColumn(name = "role_id", updatable = false)
  val role: RoleEntity,

  @Column(name = "system", updatable = false, insertable = false)
  val system: Boolean
) : Persistable<Long>()

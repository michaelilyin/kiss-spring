package net.kiss.demo.auth.entity

import net.kiss.starter.service.jpa.entity.Persistable
import javax.persistence.*

@Entity
@Table(name = "roles")
data class RoleEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  override val id: Long?,

  @Column(name = "code", updatable = false)
  val code: String,

  @Column(name = "name")
  var name: String,

  @Column(name = "description")
  var description: String,

  @Column(name = "system", updatable = false, insertable = false)
  val system: Boolean
): Persistable<Long>()

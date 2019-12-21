package net.kiss.demo.auth.entity

import net.kiss.starter.service.jpa.entity.Persistable
import javax.persistence.*

@Entity
@Table(name = "permissions")
data class PermissionEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  override val id: Long?,

  @Column(name = "code", updatable = false)
  val code: String,

  @Column(name = "name")
  var name: String,

  @Column(name = "description")
  var description: String
) : Persistable<Long>()

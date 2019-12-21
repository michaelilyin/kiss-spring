package net.kiss.demo.auth.entity

import net.kiss.starter.service.jpa.entity.Persistable
import javax.persistence.*


@Entity
@Table(name = "users")
data class UserAccountEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  override val id: Long?,

  @Column(name = "username", updatable = false)
  val username: String,

  @Column(name = "password", updatable = false)
  val password: String = "",

  @Column(name = "enabled")
  var enabled: Boolean,

  @Column(name = "first_name")
  var firstName: String,

  @Column(name = "last_name")
  var lastName: String?
): Persistable<Long>()

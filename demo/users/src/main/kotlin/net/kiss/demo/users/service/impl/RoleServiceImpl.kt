package net.kiss.demo.users.service.impl

import net.kiss.demo.users.model.Role
import net.kiss.demo.users.service.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl : RoleService {
  val ROLES = mapOf(
    Pair(1L, listOf(Role(1, "WIZZARD"), Role(2, "STRANGER"))),
    Pair(2L, listOf(Role(3, "HALFING"), Role(4, "RING-BEARER"))),
    Pair(3L, listOf(Role(5, "HUMAN"), Role(6, "KING")))
  )

  override fun getUserRoles(id: Long): List<Role> {
    return ROLES[id] ?: emptyList()
  }
}

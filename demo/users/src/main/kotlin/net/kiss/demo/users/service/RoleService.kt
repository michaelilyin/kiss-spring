package net.kiss.demo.users.service

import net.kiss.demo.users.model.Role

interface RoleService {
  fun getUserRoles(id: Long): List<Role>
}

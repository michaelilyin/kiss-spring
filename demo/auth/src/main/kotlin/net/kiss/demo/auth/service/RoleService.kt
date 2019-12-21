package net.kiss.demo.auth.service

import net.kiss.demo.auth.model.Role
import net.kiss.demo.auth.model.UserRole
import net.kiss.service.model.lists.*

interface RoleService {
  fun getUserRolesByUser(userId: Long, sort: Sort): ListResult<UserRole>
  fun findRoleById(roleId: Long): Role?
  fun getRolesPage(page: PageRequest, sort: Sort): PageResult<Role>
}

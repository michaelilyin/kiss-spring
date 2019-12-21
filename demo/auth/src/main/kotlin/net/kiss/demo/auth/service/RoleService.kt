package net.kiss.demo.auth.service

import net.kiss.demo.auth.model.Role
import net.kiss.demo.auth.model.UserRole
import net.kiss.service.model.lists.ListResult
import net.kiss.service.model.lists.SortField

interface RoleService {
  fun getUserRolesByUser(userId: Long, sort: List<SortField>): ListResult<UserRole>
  fun findRoleById(roleId: Long): Role?
}

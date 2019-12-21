package net.kiss.demo.auth.service

import net.kiss.demo.auth.model.Permission
import net.kiss.demo.auth.model.RolePermission
import net.kiss.service.model.lists.ListResult
import net.kiss.service.model.lists.PageRequest
import net.kiss.service.model.lists.PageResult
import net.kiss.service.model.lists.Sort

interface PermissionService {
  fun getRolePermissionsByRole(roleId: Long, sort: Sort): ListResult<RolePermission>
  fun findPermissionById(permissionId: Long): Permission?
  fun getPermissionsPage(page: PageRequest, sort: Sort): PageResult<Permission>
//  fun getPermissions(page: Pageable): Page<UserPermission>
}

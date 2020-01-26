package net.kiss.demo.auth.service.impl

import net.kiss.demo.auth.model.permission.Permission
import net.kiss.demo.auth.model.permission.RolePermission
import net.kiss.demo.auth.model.permission.toModel
import net.kiss.demo.auth.repository.PermissionRepository
import net.kiss.demo.auth.repository.RolePermissionGrantsRepository
import net.kiss.demo.auth.service.PermissionService
import net.kiss.service.model.lists.ListResult
import net.kiss.service.model.lists.PageRequest
import net.kiss.service.model.lists.PageResult
import net.kiss.service.model.lists.Sort
import net.kiss.starter.service.jpa.utils.toListResult
import net.kiss.starter.service.jpa.utils.toPage
import net.kiss.starter.service.jpa.utils.toPageable
import net.kiss.starter.service.jpa.utils.toSort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PermissionServiceImpl @Autowired constructor(
  private val permissionRepository: PermissionRepository,
  private val rolePermissionRepository: RolePermissionGrantsRepository
) : PermissionService {
  override fun getRolePermissionsByRole(roleId: Long, sort: Sort): ListResult<RolePermission> {
    val request = sort.toSort()
    val result = rolePermissionRepository.getAllByRoleId(roleId, request)
    return result.map { it.toModel() }.toListResult(request)
  }

  override fun findPermissionById(permissionId: Long): Permission? {
    return permissionRepository.findByIdOrNull(permissionId)?.toModel()
  }

  override fun getPermissionsPage(page: PageRequest, sort: Sort): PageResult<Permission> {
    val request = page.toPageable(sort)
    val result = permissionRepository.findAll(request)
    return result.toPage { it.toModel() }
  }
}


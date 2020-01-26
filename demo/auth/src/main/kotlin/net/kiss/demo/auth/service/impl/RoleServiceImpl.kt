package net.kiss.demo.auth.service.impl

import net.kiss.demo.auth.model.role.Role
import net.kiss.demo.auth.model.role.UserRole
import net.kiss.demo.auth.model.role.toModel
import net.kiss.demo.auth.repository.RoleRepository
import net.kiss.demo.auth.repository.UserRoleGrantsRepository
import net.kiss.demo.auth.service.RoleService
import net.kiss.service.model.lists.*
import net.kiss.starter.service.jpa.utils.toListResult
import net.kiss.starter.service.jpa.utils.toPage
import net.kiss.starter.service.jpa.utils.toPageable
import net.kiss.starter.service.jpa.utils.toSort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl @Autowired constructor(
  private val roleGrantsRepository: UserRoleGrantsRepository,
  private val roleRepository: RoleRepository
) : RoleService {
  override fun getUserRolesByUser(userId: Long, sort: Sort): ListResult<UserRole> {
    val request = sort.toSort()
    val grants = roleGrantsRepository.getAllByUserId(userId, request)
    return grants.map { it.toModel() }.toListResult(request)
  }

  override fun findRoleById(roleId: Long): Role? {
    return roleRepository.findByIdOrNull(roleId)?.toModel()
  }

  override fun getRolesPage(page: PageRequest, sort: Sort): PageResult<Role> {
    val request = page.toPageable(sort)
    val result = roleRepository.findAll(request)
    return result.toPage { it.toModel() }
  }
}

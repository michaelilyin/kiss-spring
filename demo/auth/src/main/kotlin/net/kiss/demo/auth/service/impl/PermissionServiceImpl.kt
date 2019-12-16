package net.kiss.demo.auth.service.impl

import net.kiss.demo.auth.model.UserPermission
import net.kiss.demo.auth.model.toModel
import net.kiss.demo.auth.repository.PermissionRepository
import net.kiss.demo.auth.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PermissionServiceImpl @Autowired constructor(
  private val permissionRepository: PermissionRepository
) : PermissionService {
  override fun getPermissions(page: Pageable): Page<UserPermission> {
    return permissionRepository.findAll(page).map { it.toModel() }
  }
}


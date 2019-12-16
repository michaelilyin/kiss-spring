package net.kiss.demo.auth.service

import net.kiss.demo.auth.model.UserPermission
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PermissionService {
  fun getPermissions(page: Pageable): Page<UserPermission>
}

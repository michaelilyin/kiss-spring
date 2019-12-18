package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.UserPermissionEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : CrudRepository<UserPermissionEntity, Long> {
  // language=PostgreSQL
  @Query("""
SELECT p.*
FROM permissions p
       JOIN role_permissions rp ON rp.permission_id = p.id
       JOIN user_roles ur ON ur.role_id = rp.role_id
WHERE ur.user_id = :id
""")
  fun getPermissionsByUserId(@Param("id") id: Long): List<UserPermissionEntity>
}

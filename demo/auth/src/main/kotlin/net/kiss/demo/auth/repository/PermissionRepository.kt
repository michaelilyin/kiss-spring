package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.PermissionEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : PagingAndSortingRepository<PermissionEntity, Long> {
  @Query(
    """
    from PermissionEntity p
    where p.id in (
      select p.id
      from RolePermissionGrantEntity rpg
        join UserRoleGrantEntity urg on rpg.id.roleId = urg.id.roleId
      where urg.id.userId = :id
    )
"""
  )
  fun getPermissionsByUserId(@Param("id") id: Long): List<PermissionEntity>
}

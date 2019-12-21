package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.RolePermissionGrantEntity
import net.kiss.demo.auth.entity.UserRoleGrantEntity
import net.kiss.demo.auth.entity.key.RolePermissionKey
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RolePermissionGrantsRepository : CrudRepository<RolePermissionGrantEntity, RolePermissionKey> {
  @Query("from RolePermissionGrantEntity rpg where rpg.id.roleId = :roleId")
  fun getAllByRoleId(@Param("roleId") roleId: Long, sort: Sort): List<RolePermissionGrantEntity>;
}

package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.UserRoleGrantEntity
import net.kiss.demo.auth.entity.key.UserRoleKey
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRoleGrantsRepository : CrudRepository<UserRoleGrantEntity, UserRoleKey> {
  @Query("from UserRoleGrantEntity urg where urg.id.userId = :userId")
  fun getAllByUserId(@Param("userId") userId: Long, sort: Sort): List<UserRoleGrantEntity>;
}

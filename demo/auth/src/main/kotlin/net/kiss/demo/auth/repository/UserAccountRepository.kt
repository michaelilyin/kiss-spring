package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.UserAccountEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : PagingAndSortingRepository<UserAccountEntity, Long> {
  @Query("from UserAccountEntity u where u.username = :username and u.enabled = true")
  fun findByUsername(@Param("username") username: String): UserAccountEntity?
}

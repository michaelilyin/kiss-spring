package net.kiss.demo.auth.repository

import net.kiss.demo.auth.entity.UserAccountEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : CrudRepository<UserAccountEntity, Long> {
  // language=PostgreSQL
  @Query(
    """SELECT * FROM users WHERE username = :username AND enabled = TRUE"""
  )
  fun findByUsername(@Param("username") username: String): UserAccountEntity?
}

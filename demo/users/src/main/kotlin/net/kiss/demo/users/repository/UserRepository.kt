package net.kiss.demo.users.repository

import net.kiss.demo.users.entity.UserEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
  @Query("SELECT * FROM users WHERE username = :username")
  fun findUserByUsername(@Param("username") username: String): Optional<UserEntity>
}

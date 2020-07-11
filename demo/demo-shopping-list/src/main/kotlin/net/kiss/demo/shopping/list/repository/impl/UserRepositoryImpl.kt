package net.kiss.demo.shopping.list.repository.impl

import net.kiss.demo.shopping.list.entity.UserEntity
import net.kiss.demo.shopping.list.repository.UserRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
class UserRepositoryImpl : UserRepository {

  companion object {
    val USER_ID = UUID.fromString("9eee848c-3b5a-4255-b367-09e0572099ab")

    val USERS = mapOf(
      Pair(USER_ID, UserEntity(
        id = USER_ID,
        username = "user",
        firstName = "John",
        lastName = "Doe"
      ))
    )
  }

  override fun findById(id: UUID): Mono<UserEntity> {
    return Mono.justOrEmpty(USERS[id])
  }
}

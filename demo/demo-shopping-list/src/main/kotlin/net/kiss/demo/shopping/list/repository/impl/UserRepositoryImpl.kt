package net.kiss.demo.shopping.list.repository.impl

import net.kiss.demo.shopping.list.entity.UserEntity
import net.kiss.demo.shopping.list.repository.UserRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
class UserRepositoryImpl : UserRepository {

  companion object {
    val DEFAULT_ID = UUID.fromString("9eee848c-3b5a-4255-b367-09e0572099ab")
    val USER_ID = UUID.fromString("b02649e1-5eb5-4b91-9e4a-f6ab18f60bbb")
    val ADMIN_ID = UUID.fromString("a80a9317-6673-4691-b7d1-30ab034aac79")

    val USERS = mapOf(
      Pair(DEFAULT_ID, UserEntity(
        id = DEFAULT_ID,
        username = "default",
        firstName = "System",
        lastName = "User"
      )),
      Pair(USER_ID, UserEntity(
        id = USER_ID,
        username = "user",
        firstName = "John",
        lastName = "Doe"
      )),
      Pair(ADMIN_ID, UserEntity(
        id = ADMIN_ID,
        username = "admin",
        firstName = "James",
        lastName = "Smith"
      ))
    )
  }

  override fun findById(id: UUID): Mono<UserEntity> {
    return Mono.justOrEmpty(USERS[id])
  }
}

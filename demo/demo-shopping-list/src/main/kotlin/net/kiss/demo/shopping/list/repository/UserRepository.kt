package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.UserEntity
import reactor.core.publisher.Mono
import java.util.*

interface UserRepository {
  fun findById(id: UUID): Mono<UserEntity>
}

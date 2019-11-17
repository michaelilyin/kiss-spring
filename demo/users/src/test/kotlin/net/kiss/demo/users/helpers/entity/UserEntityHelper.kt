package net.kiss.demo.users.helpers.entity

import com.github.javafaker.Faker
import net.kiss.demo.users.entity.UserEntity

open class UserEntityHelper {
  companion object : UserEntityHelper()

  val faker = Faker.instance()

  fun userEntity(
    id: Long? = faker.number().randomNumber(),
    username: String = faker.name().username()
  ) = UserEntity(
    id = id,
    username = username
  )
}

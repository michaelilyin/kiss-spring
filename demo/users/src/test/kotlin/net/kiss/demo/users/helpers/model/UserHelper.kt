package net.kiss.demo.users.helpers.model

import com.github.javafaker.Faker
import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreate

open class UserHelper {
  companion object : UserHelper()

  val faker = Faker.instance()

  fun user() = User(
    id = faker.number().randomNumber(),
    username = faker.name().username()
  )

  fun userCreate() = UserCreate(
    username = faker.name().username()
  )
}

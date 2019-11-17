package net.kiss.demo.users.service

import net.kiss.demo.users.helpers.entity.UserEntityHelper
import net.kiss.demo.users.helpers.model.UserHelper
import net.kiss.demo.users.entity.UserEntity
import net.kiss.demo.users.model.User
import net.kiss.demo.users.repository.UserRepository
import net.kiss.demo.users.service.impl.UserServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.atIndex
import org.assertj.core.data.Index
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.argThat
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@MockBean(UserRepository::class)
@SpringBootTest(classes = [UserServiceImpl::class])
class UserServiceTest @Autowired constructor(
  private val userService: UserService,
  private val userRepository: UserRepository
) {

  @Test
  fun `should find user by id`() {
    val entity = UserEntityHelper.userEntity()
    val id = entity.id!!
    given(userRepository.findById(id))
      .willReturn(Optional.of(entity))

    val res = userService.findUserById(id)

    assertThat(res?.id).isEqualTo(id)
    assertThat(res?.username).isEqualTo(entity.username)
  }

  @Test
  fun `should create new user`() {
    val user = UserHelper.userCreate()
    val arg = argThat { arg: UserEntity ->
      arg.username == user.username
    }
    val entity = UserEntityHelper.userEntity()
    given(userRepository.save(arg))
      .willReturn(entity)

    val res = userService.createUser(user)

    assertThat(res.id).isEqualTo(entity.id)
    assertThat(res.username).isEqualTo(entity.username)
  }

  @Test
  fun `should return all users`() {
    val user1 = UserEntityHelper.userEntity()
    val user2 = UserEntityHelper.userEntity()
    given(userRepository.findAll())
      .willReturn(listOf(user1, user2))

    val users = userService.getUsers()
    assertThat(users).extracting("username")
      .containsExactly(user1.username, user2.username)
  }
}

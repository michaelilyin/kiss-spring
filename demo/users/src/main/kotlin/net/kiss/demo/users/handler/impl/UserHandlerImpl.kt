package net.kiss.demo.users.handler.impl

import net.kiss.demo.users.handler.UserHandler
import net.kiss.demo.users.model.User
import net.kiss.demo.users.model.UserCreate
import net.kiss.demo.users.service.UserService
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.infra.GraphQLHandler
import org.springframework.beans.factory.annotation.Autowired

@GraphQLHandler
class UserHandlerImpl @Autowired constructor(
  private val userService: UserService
) : UserHandler {
  override fun findUser(req: GraphQLRequest): User? {
    val id = req.arg<Long>("id")

    return userService.findUserById(id)
  }

  override fun getUsers(req: GraphQLRequest): List<User> {
    return userService.getUsers()
  }

  override fun createUser(req: GraphQLRequest): User {
    val user = req.arg<UserCreate>("user")

    return userService.createUser(user)
  }

  override fun resolveUsers(req: FederationRequest<Any>): List<User> {
    val args = req.mapArgs<Long>("id")

    return userService.getAllById(args)
  }
}

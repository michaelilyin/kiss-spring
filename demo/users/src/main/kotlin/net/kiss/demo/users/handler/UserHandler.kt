package net.kiss.demo.users.handler

import net.kiss.demo.users.model.User
import net.kiss.starter.graphql.dsl.FederationRequest
import net.kiss.starter.graphql.dsl.GraphQLRequest
import org.springframework.stereotype.Component

interface UserHandler {
  fun resolveUsers(req: FederationRequest<Any>): List<User>
  fun findUser(req: GraphQLRequest): User?
  fun getUsers(req: GraphQLRequest): List<User>
  fun createUser(req: GraphQLRequest): User
}

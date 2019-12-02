package net.kiss.demo.users.handler

import net.kiss.demo.users.model.User
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.FederationResponse
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.model.LongID

interface UserHandler {
  suspend fun resolveUsers(req: FederationRequest<LongID>): FederationResponse<LongID, User>
  suspend fun findUser(req: GraphQLRequest): User?
  suspend fun getUsers(req: GraphQLRequest): List<User>
  suspend fun createUser(req: GraphQLRequest): User
}

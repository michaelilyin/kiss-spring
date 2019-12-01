package net.kiss.demo.cart.handler

import net.kiss.demo.cart.model.Cart
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

interface CartHandler {
  suspend fun resolveCarts(req: FederationRequest): List<Cart>
  suspend fun findCart(req: GraphQLRequest): Cart?
  suspend fun findUserCart(req: GraphQLRequest): Cart?
  suspend fun createCart(req: GraphQLRequest): Cart
}

package net.kiss.demo.cart.handler

import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.starter.graphql.dsl.FederationRequest
import net.kiss.starter.graphql.dsl.GraphQLRequest

interface CartHandler {
  fun resolveCarts(req: FederationRequest<Any>): List<Cart>
  fun findCart(req: GraphQLRequest): Cart?
  fun findUserCart(req: GraphQLRequest): Cart?
  fun createCart(req: GraphQLRequest): Cart
}

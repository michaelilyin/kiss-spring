package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import kotlin.reflect.KClass

open class GraphQLMutationAction<T: Any, I: Any, F>(
  val action: String,
  val contextType: KClass<T>,
  val argType: KClass<I>,
  private val parent: GraphQLMutation<T>
) {
  lateinit var fetcher: suspend (GraphQLRequest<T, I>) -> F
    get
    protected set
}

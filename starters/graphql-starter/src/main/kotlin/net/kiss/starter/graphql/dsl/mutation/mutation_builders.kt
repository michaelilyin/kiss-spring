package net.kiss.starter.graphql.dsl.mutation

import net.kiss.starter.graphql.dsl.ActionKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLMutation
import net.kiss.starter.graphql.dsl.common.GraphQLMutationAction
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType

@GraphQLMarker
class Action<T, F>(field: String, parent: LocalMutation<T>) : GraphQLMutationAction<T, F>(
  field, parent
) {
  fun execute(resolve: (GraphQLRequest) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class LocalMutation<T>(parent: GraphQLLocalType<T>) : GraphQLMutation<T>(parent) {
//  fun <A> federate(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }

  fun <R> nestedMutationContext(field: String, resolve: (GraphQLRequest) -> R) {
    val context = Action<T, R>(field, this)
    context.execute(resolve)

    addFieldMutation(field, context)
  }

  @ActionKeyword
  fun <F> action(field: String, init: Action<T, F>.() -> Unit) {
    val context = Action<T, F>(field, this)
    context.init()

    addFieldMutation(field, context)
  }
}

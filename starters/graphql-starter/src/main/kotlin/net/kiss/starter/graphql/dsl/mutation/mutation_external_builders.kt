package net.kiss.starter.graphql.dsl.mutation

import net.kiss.starter.graphql.dsl.ActionKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLMutation
import net.kiss.starter.graphql.dsl.common.GraphQLMutationAction
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLForeignType

@GraphQLMarker
class LocalAction<T, F>(field: String, parent: ForeignMutation<T>) : GraphQLMutationAction<T, F>(
  field, parent
) {
  fun execute(resolve: suspend (GraphQLRequest) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class ForeignMutation<T>(parent: GraphQLForeignType<T>) : GraphQLMutation<T>(
  parent
) {
//  fun <A> federate(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }
//
//  fun <R> nestedMutation(field: String, resolve: (GraphQLRequest) -> R) {
// TODO: maybe needed
//  }

  @ActionKeyword
  fun <F> localAction(field: String, init: LocalAction<T, F>.() -> Unit) {
    val context = LocalAction<T, F>(field, this)
    context.init()

    addFieldMutation(field, context)
  }
}

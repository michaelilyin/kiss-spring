package net.kiss.starter.graphql.dsl.mutation

import net.kiss.starter.graphql.dsl.ActionKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLMutation
import net.kiss.starter.graphql.dsl.common.GraphQLMutationAction
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLForeignType
import kotlin.reflect.KClass

@GraphQLMarker
class LocalAction<T : Any, I : Any, F>(
  field: String,
  inputType: KClass<I>,
  parent: ForeignMutation<T>
) : GraphQLMutationAction<T, I, F>(
  field, parent.type, inputType, parent
) {
  fun execute(resolve: suspend (GraphQLRequest<T, I>) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class ForeignMutation<T : Any>(
  type: KClass<T>,
  parent: GraphQLForeignType<T>
) : GraphQLMutation<T>(type, parent) {
//  fun <A> federate(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }
//
//  fun <R> nestedMutation(field: String, resolve: (GraphQLRequest) -> R) {
// TODO: maybe needed
//  }

  @ActionKeyword
  inline fun <reified I: Any, F> localAction(field: String, noinline init: LocalAction<T, I, F>.() -> Unit) {
    localAction(field, I::class, init)
  }

  @ActionKeyword
  fun <I : Any, F> localAction(field: String, inputType: KClass<I>, init: LocalAction<T, I, F>.() -> Unit) {
    val context = LocalAction<T, I, F>(field, inputType, this)
    context.init()

    addFieldMutation(field, context)
  }
}

package net.kiss.starter.graphql.dsl.mutation

import net.kiss.starter.graphql.dsl.ActionKeyword
import net.kiss.starter.graphql.dsl.FieldKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLMutation
import net.kiss.starter.graphql.dsl.common.GraphQLMutationAction
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType
import kotlin.reflect.KClass

@GraphQLMarker
class Action<T : Any, I : Any, F>(
  field: String,
  inputType: KClass<I>,
  parent: LocalMutation<T>
) : GraphQLMutationAction<T, I, F>(
  field, parent.type, inputType, parent
) {
  fun execute(resolve: suspend (GraphQLRequest<T, I>) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class LocalMutation<T : Any>(
  type: KClass<T>,
  parent: GraphQLLocalType<T>
) : GraphQLMutation<T>(type, parent) {
//  fun <A> federate(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }

  @FieldKeyword
  inline fun <reified I : Any, R : Any> nestedMutationContext(field: String, noinline resolve: suspend (GraphQLRequest<T, I>) -> R) {
    nestedMutationContext(field, I::class, resolve)
  }

  @FieldKeyword
  fun <I : Any, R : Any> nestedMutationContext(field: String, inputType: KClass<I>, resolve: suspend (GraphQLRequest<T, I>) -> R) {
    val context = Action<T, I, R>(field, inputType, this)
    context.execute(resolve)

    addFieldMutation(field, context)
  }

  @ActionKeyword
  inline fun <reified I : Any, F : Any> action(field: String, noinline init: Action<T, I, F>.() -> Unit) {
    action(field, I::class, init)
  }

  @ActionKeyword
  fun <I : Any, F : Any> action(field: String, inputType: KClass<I>, init: Action<T, I, F>.() -> Unit) {
    val context = Action<T, I, F>(field, inputType, this)
    context.init()

    addFieldMutation(field, context)
  }
}

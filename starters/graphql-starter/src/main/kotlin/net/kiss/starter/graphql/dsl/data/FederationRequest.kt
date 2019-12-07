package net.kiss.starter.graphql.dsl.data

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

data class FederationRequestItem<KEY : Any>(
  internal val index: Int,
  internal val data: Map<String, Any>
) {
  internal fun asKey(type: KClass<KEY>): KEY {
    val constructor = type.primaryConstructor ?: throw IllegalArgumentException()
    val params = mutableMapOf<KParameter, Any?>()
    constructor.parameters.forEach {
      params[it] = data[it.name]
    }

    return constructor.callBy(params)
  }
}

data class FederationRequestResult<T>(
  val index: Int,
  val result: T
)

class FederationRequest<KEY : Any>(
  private val keyType: KClass<KEY>,
  val items: List<FederationRequestItem<KEY>>
) {
  val keys: List<KEY>
    get() {
      return items.map { it.asKey(keyType) }
    }

  internal fun <TYPE> toResponse(response: Collection<TYPE>, keyExtractor: (TYPE) -> KEY): FederationResponse<TYPE> {
    val responseMap = response.groupBy(keyExtractor)
      .mapValues { it.value.first() }

    val responseItems = items.map { FederationRequestResult(it.index, responseMap[it.asKey(keyType)]) }

    return FederationResponse(responseItems)
  }
}

fun <T, K : Any> Collection<T>.toFederationResponse(
  request: FederationRequest<K>,
  keyExtractor: (T) -> K
): FederationResponse<T> {
  return request.toResponse(this, keyExtractor)
}

package net.kiss.starter.grqphql.federation

import com.expediagroup.graphql.federation.execution.FederatedTypeResolver
import kotlin.reflect.KClass

interface FederationResolver<T: Any> : FederatedTypeResolver<T> {
  val type: KClass<T>
}

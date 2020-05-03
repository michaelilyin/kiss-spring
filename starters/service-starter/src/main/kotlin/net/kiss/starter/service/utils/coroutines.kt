package net.kiss.starter.service.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.slf4j.MDCContext
import reactor.core.publisher.Mono

fun <T> returnMono(
  block: suspend CoroutineScope.() -> T?
): Mono<T> {
  return mono(MDCContext(), block)
}

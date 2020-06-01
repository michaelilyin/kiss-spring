package net.kiss.starter.service.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun <T> returnMono(
  block: suspend CoroutineScope.() -> T?
): Mono<T> {
  return mono(MDCContext(), block)
}

suspend fun <T> Flux<T>.awaitList(): List<T> {
  return collectList().awaitFirst()
}

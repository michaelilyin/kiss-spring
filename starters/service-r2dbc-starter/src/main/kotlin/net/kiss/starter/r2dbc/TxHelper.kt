package net.kiss.starter.r2dbc

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.slf4j.MDCContext
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

open class TxHelper {
  /**
   * Temporary coroutines TX workaround until spring 5.3 released
   */
  @Transactional
  fun <T> wrap(block: suspend CoroutineScope.() -> T): Mono<T> {
    return mono(MDCContext(), block)
  }
}

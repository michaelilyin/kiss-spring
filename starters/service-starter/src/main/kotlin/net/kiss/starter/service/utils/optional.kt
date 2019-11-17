package net.kiss.starter.service.utils

import java.util.*

fun <T> Optional<T>.orNull(): T? {
  return orElse(null)
}

package net.kiss.service.interop

import java.util.function.BiFunction

inline fun <reified T, reified U, reified R> bifunction(noinline f: (T, U) -> R): BiFunction<T, U, R> {
  return BiFunction<T, U, R> { t, u ->
    f(t, u)
  }
}

fun String?.emptyAsNull(): String? {
  return if (this?.isBlank() != false) null else this
}

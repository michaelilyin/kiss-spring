package net.kiss.service.exception

open class ServiceException(
  message: String? = null,
  cause: Throwable? = null
) : RuntimeException(message, cause) {
}

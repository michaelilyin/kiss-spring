package net.kiss.service.exception

class UnexpectedNullException(
  message: String? = null,
  cause: Throwable? = null
) : ServiceException(message, cause) {
}

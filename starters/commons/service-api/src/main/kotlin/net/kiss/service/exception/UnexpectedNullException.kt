package net.kiss.service.exception

class UnexpectedNullException(
  message: String,
  cause: Throwable? = null
) : ServiceException(message, cause) {
}

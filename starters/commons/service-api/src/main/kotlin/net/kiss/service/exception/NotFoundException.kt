package net.kiss.service.exception

class NotFoundException(
  message: String,
  cause: Throwable? = null
) : ServiceException(message, cause) {
}

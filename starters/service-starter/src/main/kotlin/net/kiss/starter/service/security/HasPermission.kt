package net.kiss.starter.service.security

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Repeatable
annotation class HasPermission(
  vararg val value: String
)

package net.kiss.starter.r2dbc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("kiss.r2bc")
@ConstructorBinding
data class R2DBCProperties(
  val host: String,
  val database: String,
  val schema: String,
  val username: String,
  val password: String,
  val port: Int
) {
}

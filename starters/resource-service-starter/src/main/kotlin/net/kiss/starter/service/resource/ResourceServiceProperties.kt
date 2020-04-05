package net.kiss.starter.service.resource

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConstructorBinding
@ConfigurationProperties("resource")
class ResourceServiceProperties (
  @DefaultValue("/api/public/**")
  val publicApi: List<String>
) {
}

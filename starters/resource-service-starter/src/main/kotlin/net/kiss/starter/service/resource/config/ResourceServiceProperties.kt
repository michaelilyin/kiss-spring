package net.kiss.starter.service.resource.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("resource")
class ResourceServiceProperties {
  var publicUrls: List<String> = listOf()
}

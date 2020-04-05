package net.kiss.starter.service.resource

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertySource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import java.io.IOException

class AuthEnv: EnvironmentPostProcessor {
  private val loader = YamlPropertySourceLoader()

  override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
//    val path = ClassPathResource("application-auth.yml")
//    val propertySource = loadYaml(path)
//    environment.propertySources.addLast(propertySource)
  }

  private fun loadYaml(path: Resource): PropertySource<*> {
    return try {
      this.loader.load("application-auth", path)[0]
    } catch (ex: IOException) {
      throw IllegalStateException("Failed to load yaml configuration from $path", ex)
    }
  }
}

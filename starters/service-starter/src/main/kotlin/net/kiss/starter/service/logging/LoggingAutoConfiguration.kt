package net.kiss.starter.service.logging

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingAutoConfiguration {
  @Bean
  fun loggingBean() = Slf4jMDCFilter()
}

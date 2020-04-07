package net.kiss.starter.service.logging

import net.kiss.auth.model.CurrentUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class LoggingAutoConfig {
  @Bean
  fun loggingFilterBean() = Slf4jMDCFilter()

//  @Bean
//  fun loggingFilter(filter: Slf4jMDCFilter): FilterRegistrationBean<Slf4jMDCFilter> {
//    val registrationBean = FilterRegistrationBean<Slf4jMDCFilter>()
//    registrationBean.setFilter(Slf4jMDCFilter())
//    registrationBean.addUrlPatterns("/users/*")
//    return registrationBean
//  }
}

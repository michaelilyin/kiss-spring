package net.kiss.starter.service.auth

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [AuthServiceAutoConfig::class])
class AuthServiceAutoConfig {
}

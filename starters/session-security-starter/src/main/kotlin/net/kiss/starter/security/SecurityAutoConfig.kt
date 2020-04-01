package net.kiss.starter.security

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SecurityAutoConfig::class])
class SecurityAutoConfig {
}

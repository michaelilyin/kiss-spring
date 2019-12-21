package net.kiss.starter.service.jpa

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [ServiceJpaAutoConfig::class])
class ServiceJpaAutoConfig {

}

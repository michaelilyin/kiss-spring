package net.kiss.starter.service.facade

import org.springframework.stereotype.Service
import java.lang.annotation.Inherited

@Service
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Facade {
}

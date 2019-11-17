plugins {
  id("kotlin-spring-boot-starter")
}

val kotlinLoggingVer: String by extra
val springDataVer: String by extra

dependencies {
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.2")
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.boot:spring-boot-starter-aop")
  compile("org.springframework.boot:spring-boot-starter-webflux")
  compileOnly("org.springframework.boot:spring-boot-starter-security")
//    compileOnly("org.springframework.data:spring-data-commons:$springDataVer")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")

//    compile("io.springfox:springfox-swagger2:2.9.2")
//    compile("io.springfox:springfox-swagger-ui:2.9.2")
//
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
}

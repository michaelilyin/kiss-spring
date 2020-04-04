plugins {
  id("kotlin-spring-boot-starter")
}

val kotlinLoggingVer: String by extra
val springDataVer: String by extra
val springbootVer: String by extra

dependencies {
  api(project(":starters:commons:auth-api"))
  api(project(":starters:commons:service-api"))
  api("io.github.microutils:kotlin-logging:$kotlinLoggingVer")

  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.5")
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.5")
  compile("io.projectreactor.kotlin:reactor-kotlin-extensions")
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.boot:spring-boot-starter-aop")
  compile("org.springframework.boot:spring-boot-starter-webflux")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")

  compileOnly("org.springframework.boot:spring-boot-starter-security")
  compileOnly("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:$springbootVer")
  compileOnly("org.springframework.data:spring-data-commons:$springDataVer")

//    compile("io.springfox:springfox-swagger2:2.9.2")
//    compile("io.springfox:springfox-swagger-ui:2.9.2")
}

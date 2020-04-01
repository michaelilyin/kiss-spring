plugins {
  id("kotlin-spring-boot-starter")
}

val kotlinLoggingVer: String by extra
val springDataVer: String by extra
val springbootVer: String by extra
val springVer: String by extra

dependencies {
  api(project(":starters:commons:auth-api"))
  compileOnly(project(":starters:commons:service-api"))
  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")

  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.5")
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.5")
  compile("org.springframework.boot:spring-boot-starter-security")
  compile("org.springframework.session:spring-session-core:2.2.2.RELEASE")

  compileOnly("org.springframework.boot:spring-boot-starter-webflux")
  compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
  compileOnly("org.springframework.data:spring-data-commons:$springDataVer")

//    compile("io.springfox:springfox-swagger2:2.9.2")
//    compile("io.springfox:springfox-swagger-ui:2.9.2")
}

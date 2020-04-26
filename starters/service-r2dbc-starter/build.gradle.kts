plugins {
  id("kotlin-spring-boot-starter")
}

val springbootVer: String by extra
val springVer: String by extra
val springSecurityOauth2Ver: String by extra
val kotlinLoggingVer: String by extra

dependencies {
  api("org.springframework.boot:spring-boot-autoconfigure:$springbootVer")
  api("org.springframework.data:spring-data-r2dbc:1.0.0.RELEASE")
  api("org.springframework.boot:spring-boot-starter-data-jdbc")

  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")

  runtimeOnly("org.postgresql:postgresql")
  implementation("io.r2dbc:r2dbc-postgresql:0.8.2.RELEASE")
}

plugins {
  id("kotlin-spring-boot-starter")
}

val springVer: String by extra
val testcontainersVer: String by extra
val junitVer: String by extra
val fakerVer: String by extra

dependencies {
  compile("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "junit")
  }
  compile("org.springframework.security:spring-security-test:$springVer") {
    exclude(module = "junit")
  }

  compile("org.testcontainers:junit-jupiter:$testcontainersVer") {
    exclude(module = "junit")
  }

  compile("org.junit.jupiter:junit-jupiter-api:$junitVer")
  compile("com.github.javafaker:javafaker:$fakerVer")
  compile("org.testcontainers:postgresql:$testcontainersVer")
  implementation("org.junit.jupiter:junit-jupiter-engine:$junitVer")
}

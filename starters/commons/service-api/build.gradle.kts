plugins {
  id("kotlin-module")
}

val springDataVer: String by extra

dependencies {
  compileOnly("org.springframework.data:spring-data-commons:$springDataVer")
}

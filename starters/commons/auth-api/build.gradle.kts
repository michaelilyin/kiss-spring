plugins {
  id("kotlin-module")
}

val coroutinesVer: String by extra

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVer")
}

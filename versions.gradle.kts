@file:Suppress("UNUSED_VARIABLE")

allprojects {
  apply {
    from("$rootDir/buildSrc/build-versions.gradle.kts")
  }

  val springVer by extra("5.2.3.RELEASE")
  val springDataVer by extra("2.2.5.RELEASE")
  val graphqlKotlin by extra("2.0.0.RC1")
//  val graphqlKotlin by extra("1.4.2")
  val junitVer by extra("5.3.2")
  val fakerVer by extra("0.17.2")
  val kotlinLoggingVer by extra("1.6.24")
  val testcontainersVer by extra("1.11.3")
}

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.spotless)
  `java-gradle-plugin`
}

group = "ca.derekellis"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(gradleApi())
  implementation(libs.kotlinPoet)
  implementation(libs.css)
  implementation(libs.kotlin.gradlePlugin)

  testImplementation(libs.assertK)
  testImplementation(libs.junit)
}

gradlePlugin {
  plugins {
    create("css") {
      id = "ca.derekellis.css"
      implementationClass = "ca.derekellis.css.gradle.CssGradlePlugin"
    }
  }
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("src/test/fixtures/**/*.*")
    ktlint(libs.versions.ktlint.get()).editorConfigOverride(
      mapOf(
        "indent_size" to "2",
        "disabled_rules" to "package-name",
        "ij_kotlin_allow_trailing_comma" to "true",
        "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
      )
    )
    trimTrailingWhitespace()
    endWithNewline()
  }
}

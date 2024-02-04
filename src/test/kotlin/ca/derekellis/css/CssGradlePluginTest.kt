package ca.derekellis.css

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Test
import java.io.File

class CssGradlePluginTest {
  @Test
  fun `gradle task works`() {
    val root = File("src/test/fixtures/basic-project")

    val gradleRunner = GradleRunner.create()
      .withProjectDir(root)
      .withArguments(":clean", ":build")

    val result = gradleRunner.build()
    assertThat(result.task(":generateJsMainCssRefs")?.outcome).isEqualTo(TaskOutcome.SUCCESS)

    val generatedSource = root.resolve("build/generated/css/jsMain/com/test/styleCss.kt")
    assertThat(generatedSource.readText()).isEqualTo(
      """
      |package com.test
      |
      |import kotlin.String
      |
      |public object styleCss {
      |  public const val helloWorld: String = "helloWorld"
      |
      |  public const val anotherOne: String = "anotherOne"
      |}
      |
    """.trimMargin()
    )
  }
}

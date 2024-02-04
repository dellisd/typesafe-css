package ca.derekellis.css.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

class CssGradlePlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.plugins.withId("org.jetbrains.kotlin.multiplatform") {
      val extension = project.extensions.create("css", CssExtension::class.java)
      val kotlinExtension = project.kotlinExtension as KotlinMultiplatformExtension
      val configuredSourceSets = mutableSetOf<String>()

      kotlinExtension.targets.configureEach { target ->
        target.compilations.configureEach { compilation ->
          compilation.kotlinSourceSets.forEach { sourceSet ->
            if (!configuredSourceSets.add(sourceSet.name)) return@forEach
            val task = project.registerTask(sourceSet, extension)

            project.tasks.named(compilation.compileKotlinTaskName) {
              it.dependsOn(task)
            }
          }
        }
      }
    }
  }

  private fun Project.registerTask(sourceSet: KotlinSourceSet, extension: CssExtension): TaskProvider<GenerateCssRefsTask> {
    val outputDir = layout.buildDirectory.dir("generated/css/${sourceSet.name}")
    outputDir.get().asFile.mkdirs()
    sourceSet.kotlin.srcDirs(outputDir)

    return tasks.register(
      "generate${sourceSet.name.replaceFirstChar { it.uppercase() }}CssRefs",
      GenerateCssRefsTask::class.java,
    ) { task ->
      task.targetPackageName.set(extension.packageName)
      task.inputFiles.from(sourceSet.resources.sourceDirectories.asFileTree.filter { it.extension == "css" })
      task.outputDirectory.set(outputDir)
    }
  }
}

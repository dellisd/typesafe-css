package ca.derekellis.css.gradle

import ca.derekellis.css.CssProcessor
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

abstract class GenerateCssRefsTask : DefaultTask() {
  @get:InputFiles
  @get:SkipWhenEmpty
  abstract val inputFiles: ConfigurableFileCollection

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @get:Input
  abstract val targetPackageName: Property<String>

  @TaskAction
  fun generate() {
    val cssProcessor = CssProcessor()

    val packageName = targetPackageName.get()
    val outputDirectory = outputDirectory.get().asFile

    inputFiles.files.forEach { file ->
      val fileSpec = cssProcessor.process(file.inputStream(), file.nameWithoutExtension, packageName)
      fileSpec.writeTo(directory = outputDirectory)
    }
  }
}

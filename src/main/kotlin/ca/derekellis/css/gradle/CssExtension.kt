package ca.derekellis.css.gradle

import org.gradle.api.provider.Property

abstract class CssExtension {
  abstract val packageName: Property<String>
}

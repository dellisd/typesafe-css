package ca.derekellis.css

import com.helger.css.reader.CSSReader
import com.helger.css.reader.CSSReaderSettings
import com.squareup.kotlinpoet.FileSpec
import java.io.InputStream

class CssProcessor {
  private val settings = CSSReaderSettings()

  fun process(input: InputStream, sourceFileName: String, targetPackage: String): FileSpec {
    val css = CSSReader.readFromReader({ input.reader() }, settings)
    requireNotNull(css) { "Failed to parse CSS" }

    val classNamesSet = mutableSetOf<String>()

    css.allStyleRules.forEach { rule ->
      rule.allSelectors.forEach { selector ->
        selector.allMembers.forEach { member ->
          if (member.asCSSString.startsWith(".")) {
            classNamesSet.add(member.asCSSString.removePrefix("."))
          }
        }
      }
    }

    val name = "${sourceFileName}Css"
    val refObject = buildObject(name, classNamesSet)
    return FileSpec.builder(targetPackage, name)
      .addType(refObject)
      .build()
  }
}

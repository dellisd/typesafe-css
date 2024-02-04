package ca.derekellis.css

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

fun buildObject(name: String, classNames: Set<String>): TypeSpec {
  return TypeSpec.objectBuilder(name)
    .apply {
      classNames.forEach { ref -> addProperty(propSpec(ref)) }
    }
    .build()
}

private fun propSpec(className: String): PropertySpec =
  PropertySpec.builder(className, String::class, KModifier.CONST)
    .initializer("%S", className)
    .build()

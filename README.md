# typesafe-css (better name TBD)

Generate typesafe CSS classname accessors for Kotlin/JS browser apps, akin to [CSS Modules].

## Setup

(TODO)

```kotlin
plugins {
  id("ca.derekellis.ca")
}

css {
  packageName.set("com.example")
}
```

## Usage

Write your styles in `*.css` files under your project's `resources/` directory, and then run the
`:generateJsMainCssRefs` task on your project.

This will generate an object that maps the classnames defined in your stylesheet to property accessors that you can use
in your code.

### Example

```css
/* resources/app.css */
.content {
  background: #212121;
  color: #FAFAFA;
}
```

```kotlin
/* jsMain/kotlin/App.kt */
import com.example.appCss

// kotlinx.html
div(classes = appCss.content) {}

// compose-html
Div(attrs = { classes(appCss.content) }) {}
```

### Continuous Generation

If you are frequently making changes to your stylesheet, you can use the `--continuous` gradle flag while running
the `:generateJsMainCssRefs` task which will automatically re-run the task any time your source files have changed.

i.e. `./gradlew :generateJsMainCssRefs --continuous`

[CSS Modules]: https://github.com/css-modules/css-modules

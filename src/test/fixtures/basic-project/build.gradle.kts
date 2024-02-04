plugins {
  kotlin("multiplatform") version "1.9.22"
  id("ca.derekellis.css")
}

kotlin {
  js {
    browser()
  }
}

css {
  packageName.set("com.test")
}
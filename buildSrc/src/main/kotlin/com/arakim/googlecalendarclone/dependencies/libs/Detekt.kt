package com.arakim.googlecalendarclone.dependencies.libs

object Detekt {
    // should match with version in buildSrc/build.gradle.kts
    const val Version = "1.23.3"
    const val Plugin = "io.gitlab.arturbosch.detekt"
    const val Classpath = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$Version"
    const val Formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$Version"
}
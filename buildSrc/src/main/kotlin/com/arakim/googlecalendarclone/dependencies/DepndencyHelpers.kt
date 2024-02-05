package com.arakim.googlecalendarclone.dependencies

import com.arakim.googlecalendarclone.dependencies.libs.AndroidX
import com.arakim.googlecalendarclone.dependencies.libs.Compose
import com.arakim.googlecalendarclone.dependencies.libs.Hilt
import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.androidX() {
    implementation(AndroidX.Core.Ktx)
    implementation(AndroidX.Lifecycle.RunTime)
}

fun DependencyHandler.compose() {
    implementationPlatform(Compose.BOM)

    implementation(Compose.Activity)
    implementation(Compose.Ui)
    implementation(Compose.UiGraphics)
    implementation(Compose.Preview)
    implementation(Compose.Material3)

    debugImplementation(Compose.UiTooling)
    debugImplementation(Compose.UiTestManifest)
}

fun DependencyHandler.hilt() {
    implementation(Hilt.Android)
    kapt(Hilt.AndroidCompiler)
}

fun DependencyHandler.hiltComposeNavigation() {
    implementation(Hilt.ComposeNavigation)
}
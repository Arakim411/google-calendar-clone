package com.arakim.googlecalendarclone.dependencies

import com.arakim.googlecalendarclone.dependencies.libs.AndroidX
import com.arakim.googlecalendarclone.dependencies.libs.Compose
import com.arakim.googlecalendarclone.dependencies.libs.Hilt
import com.arakim.googlecalendarclone.dependencies.libs.Kotlin
import com.arakim.googlecalendarclone.dependencies.libs.Moshi
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
    implementation(AndroidX.Lifecycle.Compose)
    implementation(Compose.Navigation)

    debugImplementation(Compose.UiTooling)
    debugImplementation(Compose.UiTestManifest)
}

fun DependencyHandler.composeHiltNavigation() {
    implementation(Compose.HiltNavigation)
}

fun DependencyHandler.hilt() {
    implementation(Hilt.Android)
    kapt(Hilt.AndroidCompiler)
}

fun DependencyHandler.mvi() {
    implementationProject(":util:mvi")
}

fun DependencyHandler.kotlin() {
    implementation(Kotlin.Coroutines.Core)
}

fun DependencyHandler.moshi() {
    implementation(Moshi.Core)
    implementation(Moshi.Kotlin)
}

fun DependencyHandler.hiltComposeNavigation() {
    implementation(Hilt.ComposeNavigation)
}
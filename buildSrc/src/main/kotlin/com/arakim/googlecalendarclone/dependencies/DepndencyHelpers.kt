package com.arakim.googlecalendarclone.dependencies

import com.arakim.googlecalendarclone.dependencies.libs.AndroidX
import com.arakim.googlecalendarclone.dependencies.libs.Compose
import com.arakim.googlecalendarclone.dependencies.libs.Hilt
import com.arakim.googlecalendarclone.dependencies.libs.Kotlin
import com.arakim.googlecalendarclone.dependencies.libs.Moshi
import com.arakim.googlecalendarclone.dependencies.libs.Tests
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

fun DependencyHandler.jvmTests() {
    testImplementationProject(":util:test")

    testImplementation(Tests.Junit5Jupiter.Core)
    testImplementation(Tests.Junit5Jupiter.Api)
    testImplementation(Tests.Junit5Jupiter.Engine)
    testImplementation(Tests.Junit5Jupiter.Params)
    testImplementation(Tests.AssertK.Jvm)
    testImplementation(Tests.Mockk.Core)
    testImplementation(Tests.Mockk.Agent)
    testImplementation(Tests.Turbine.Core)
    testImplementation(Kotlin.Coroutines.Core)
    testImplementation(Tests.Coroutines.Core)
    testImplementation(Tests.Turbine.Core)
}

fun DependencyHandler.androidTests(util: Boolean = true) {
    androidTestImplementationProject(":util:test")
    androidTestImplementation(Tests.Espresso.Android)
    androidTestImplementation(Tests.Mockk.Android)
    androidTestImplementation(Tests.Mockk.Android)
    androidTestImplementation(Tests.Mockk.Agent)
    androidTestImplementation(Tests.AssertK.Jvm)
    androidTestImplementation(Tests.Coroutines.Core)
    androidTestImplementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")

    if(util) {
        androidTestImplementationProject(":util:android-test")
    }
}

fun DependencyHandler.hiltTests() {
    androidTestImplementation(Tests.HiltTests.Android)
    kapt(Tests.HiltTests.Compiler)

}

fun DependencyHandler.composeTest() {
    androidTests()
    androidTestImplementation(Tests.Junit4.Compose)
}

fun DependencyHandler.hiltComposeNavigation() {
    implementation(Hilt.ComposeNavigation)
}
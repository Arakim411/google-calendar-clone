import com.arakim.googlecalendarclone.dependencies.androidTests
import com.arakim.googlecalendarclone.dependencies.hiltTests
import com.arakim.googlecalendarclone.dependencies.libs.Tests
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidModulePlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `kotlin-kapt`
}

apply<AndroidModulePlugin>()

dependencies {
    implementation(Tests.AndroidX.Runner)
    implementation(Tests.HiltTests.Android)
    androidTests(util = false)
    hiltTests()
}

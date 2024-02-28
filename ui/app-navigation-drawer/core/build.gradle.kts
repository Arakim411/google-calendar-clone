
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.dependencies.composeTest
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.jvmTests
import com.arakim.googlecalendarclone.dependencies.mvi
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidComposeModulePlugin

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
    `kotlin-kapt`
}

apply<AndroidComposeModulePlugin>()

dependencies {

    jvmTests()
    composeTest()

    hilt()
    compose()
    composeTest()
    mvi()
}
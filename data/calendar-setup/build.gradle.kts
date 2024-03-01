
import com.arakim.googlecalendarclone.dependencies.androidTests
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.jvmTests
import com.arakim.googlecalendarclone.dependencies.moshi
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidModulePlugin

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
    `kotlin-kapt`
}

apply<AndroidModulePlugin>()

dependencies {
    implementation(project(":domain:calendar-setup"))

    moshi()
    androidTests()
    jvmTests()
    hilt()
}
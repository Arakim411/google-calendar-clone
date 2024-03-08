import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.moshi
import com.arakim.googlecalendarclone.dependencies.retrofit
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

    implementation(project(":data:sign-in"))

    retrofit(api = true)
    moshi()
    hilt()
}
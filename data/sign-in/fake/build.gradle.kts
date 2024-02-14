import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidModulePlugin

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    `kotlin-kapt`
}

apply<AndroidModulePlugin>()

dependencies {
    implementation(project(":data:sign-in:common"))
    hilt()
}
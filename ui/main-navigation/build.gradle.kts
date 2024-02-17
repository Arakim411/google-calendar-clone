
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.dependencies.composeHiltNavigation
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.mvi
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidComposeModulePlugin

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    `kotlin-kapt`
}

apply<AndroidComposeModulePlugin>()

dependencies {
    implementation(project(":ui:screen:home"))
    implementation(project(":ui:screen:sign-in"))
    implementation(project(":ui:screen:splash"))

    hilt()
    composeHiltNavigation()
    compose()
    mvi()
}
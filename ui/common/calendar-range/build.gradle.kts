
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidComposeModulePlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    `kotlin-kapt`
}

apply<AndroidComposeModulePlugin>()

dependencies {

    api(project(":domain:calendar"))
    api(project(":domain:calendar-setup"))

    compose(common = true)
    hilt()
}
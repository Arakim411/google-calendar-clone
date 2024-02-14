import com.arakim.googlecalendarclone.dependencies.moshi
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidModulePlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply<AndroidModulePlugin>()

dependencies {
    api(project(":domain:user:sign-in"))
    moshi()
}
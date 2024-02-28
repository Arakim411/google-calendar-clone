
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.gradlebuild.plugins.AndroidComposeModulePlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply<AndroidComposeModulePlugin>()

dependencies {

    compose(common = false)
}
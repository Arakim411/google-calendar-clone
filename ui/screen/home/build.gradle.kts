
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.dependencies.composeHiltNavigation
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.libs.Google
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
    implementation(project(":domain:user:sign-in"))

    implementation(Google.Api.AndroidClient)
    implementation(Google.PlayServices.Auth)

    hilt()
    composeHiltNavigation()
    compose()
    mvi()
}
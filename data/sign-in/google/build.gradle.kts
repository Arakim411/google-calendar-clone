import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.libs.Google
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

    implementation(Google.PlayServices.Auth)
    implementation(Google.Api.AndroidClient)
    implementation(Google.Api.Calendar)
    hilt()
}
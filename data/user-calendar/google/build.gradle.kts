import com.arakim.googlecalendarclone.dependencies.androidTests
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.jvmTests
import com.arakim.googlecalendarclone.dependencies.libs.Google
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
    implementation(project(":domain:calendar:user-calendar"))
    implementation(project(":domain:user:sign-in"))

    implementation(project(":data:sign-in:google"))
    implementation(project(":data:sign-in"))
    implementation(project(":data:user-tasks"))

    implementation(Google.PlayServices.Auth)
    implementation(Google.Api.Calendar)
    implementation(Google.Api.AndroidClient)

    moshi()
    androidTests()
    jvmTests()
    hilt()
}
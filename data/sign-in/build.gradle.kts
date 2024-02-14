
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
    api(project(":domain:user:sign-in"))
    implementation(project(":data:sign-in:common"))
    implementation(project(":data:sign-in:google"))
    implementation(project(":data:sign-in:fake"))

    hilt()
}
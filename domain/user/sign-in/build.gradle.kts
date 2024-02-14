import com.arakim.googlecalendarclone.dependencies.libs.Hilt
import com.arakim.googlecalendarclone.gradlebuild.plugins.KotlinModulePlugin

plugins {
    kotlin("jvm")
}

apply<KotlinModulePlugin>()

dependencies {
    api(project(":domain:user"))
    implementation(Hilt.Dagger)
}
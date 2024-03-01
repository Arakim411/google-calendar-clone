import com.arakim.googlecalendarclone.dependencies.libs.Hilt
import com.arakim.googlecalendarclone.gradlebuild.plugins.KotlinModulePlugin

apply<KotlinModulePlugin>()

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Hilt.Dagger)
}

import com.arakim.googlecalendarclone.dependencies.kotlin
import com.arakim.googlecalendarclone.dependencies.libs.Kotlin
import com.arakim.googlecalendarclone.dependencies.libs.Tests

plugins {
    kotlin("jvm")
}

dependencies {
    kotlin()
    implementation(Tests.Junit5Jupiter.Core)
    implementation(Tests.Junit5Jupiter.Api)
    implementation(Tests.Junit5Jupiter.Engine)
    implementation(Tests.Junit5Jupiter.Params)
    implementation(Tests.AssertK.Jvm)
    implementation(Tests.Mockk.Core)
    implementation(Tests.Mockk.Agent)
    implementation(Tests.Turbine.Core)
    implementation(Kotlin.Coroutines.Core)
    implementation(Tests.Coroutines.Core)
}
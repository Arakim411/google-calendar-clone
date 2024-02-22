package com.arakim.googlecalendarclone.dependencies.libs

object Tests {

    object AndroidX {
        const val Runner = "androidx.test:runner:1.5.2"
    }

    object Espresso {
        const val Android = "androidx.test.espresso:espresso-core:3.5.0"
    }

    object AssertK {
        const val Jvm = "com.willowtreeapps.assertk:assertk-jvm:0.26.1"
    }

    object Junit4 {
        const val Compose = "androidx.compose.ui:ui-test-junit4"
    }

    object Junit5Jupiter {
        const val Version = "5.8.1"

        const val Core = "org.junit.jupiter:junit-jupiter:${Version}"
        const val Api = "org.junit.jupiter:junit-jupiter-api:${Version}"
        const val Engine = "org.junit.jupiter:junit-jupiter-engine:${Version}"
        const val Params = "org.junit.jupiter:junit-jupiter-params:${Version}"
    }

    object Mockk {
        const val Version = "1.13.9"
        const val Core = "io.mockk:mockk:$Version"
        const val Android = "io.mockk:mockk-android:$Version"
        const val Agent = "io.mockk:mockk-agent:$Version"
    }

    object HiltTests {
        const val Android = "com.google.dagger:hilt-android-testing:${Hilt.Version}"
        const val Compiler = "androidx.hilt:hilt-compiler:1.1.0"
    }

    object Turbine {
        const val Version = "1.0.0"
        const val Core = "app.cash.turbine:turbine:$Version"
    }

    object Coroutines {
        const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Kotlin.Coroutines.Version}"
    }

    object JUnit {
        const val Core = "junit:junit:4.13.2"
    }
}
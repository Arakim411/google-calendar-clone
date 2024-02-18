package com.arakim.googlecalendarclone.dependencies.libs

object AndroidX {

    object Core {
        const val Version = "1.12.0"
        const val SplashVersion = "1.0.0"

        const val Ktx = "androidx.core:core-ktx:$Version"
        const val SplashScreen = "androidx.core:core-splashscreen:$SplashVersion"
    }

    object Lifecycle {
        const val Version = "2.7.0"

        const val RunTime = "androidx.lifecycle:lifecycle-runtime-ktx:$Version"
        const val Compose = "androidx.lifecycle:lifecycle-runtime-compose:$Version"
    }


}
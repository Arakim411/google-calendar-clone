package com.arakim.googlecalendarclone.dependencies.libs

object Hilt {
    // should match with version from buildSrc/build.gradle.kts
    const val Version = "2.48"

    const val Plugin = "com.google.dagger.hilt.android"

    const val Android = "com.google.dagger:hilt-android:$Version"
    const val AndroidCompiler = "com.google.dagger:hilt-compiler:$Version"

    const val ComposeNavigation = "androidx.hilt:hilt-navigation-compose:1.1.0"

}
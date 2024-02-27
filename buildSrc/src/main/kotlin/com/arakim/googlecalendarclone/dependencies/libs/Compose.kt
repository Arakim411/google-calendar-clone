package com.arakim.googlecalendarclone.dependencies.libs

object Compose {

    const val KotlinCompilerExtensionVersion = "1.5.1"
    const val NavigationVersion = "2.7.7"
    const val HiltNavigationVersion = "1.0.0-alpha03"

    const val BOM = "androidx.compose:compose-bom:2024.01.00"

    const val Activity = "androidx.activity:activity-compose:1.8.2"

    const val Ui = "androidx.compose.ui:ui"
    const val UiGraphics = "androidx.compose.ui:ui-graphics"
    const val Preview = "androidx.compose.ui:ui-tooling-preview"
    //temporary solution due https://issuetracker.google.com/issues/322214617
    const val Material3 = "androidx.compose.material3:material3-android:1.2.0-alpha10"
    const val Navigation = "androidx.navigation:navigation-compose:$NavigationVersion"
    const val HiltNavigation = "androidx.hilt:hilt-navigation-compose:$HiltNavigationVersion"
    const val WindowSizeClass = "androidx.compose.material3:material3-window-size-class"

    //only debug implementation
    const val UiTooling = "androidx.compose.ui:ui-tooling"
    const val UiTestManifest = "androidx.compose.ui:ui-test-manifest"
}
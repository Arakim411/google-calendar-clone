plugins {
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")

    // should match with version in dependencies/libs/Hilt.kt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.48")

}
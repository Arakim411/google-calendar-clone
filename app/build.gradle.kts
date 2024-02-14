
import com.arakim.googlecalendarclone.dependencies.androidX
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.libs.Compose
import com.arakim.googlecalendarclone.gradlebuild.Android
import com.arakim.googlecalendarclone.gradlebuild.BuildJvm

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    `kotlin-kapt`
    id("com.google.dagger.hilt.android")
}

android {
    namespace = Android.BaseApplicationId
    compileSdk = Android.CompileSdk

    defaultConfig {
        applicationId = Android.BaseApplicationId
        minSdk = Android.MinSdk
        targetSdk = Android.TargetSdk

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = BuildJvm.JvmTarget
        targetCompatibility = BuildJvm.JvmTarget
    }
    kotlinOptions {
        jvmTarget = BuildJvm.JvmTarget.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.KotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data:sign-in"))
    implementation(project(":ui:main-navigation"))

    androidX()
    compose()
    hilt()


    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
}
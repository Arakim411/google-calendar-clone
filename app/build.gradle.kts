import com.arakim.googlecalendarclone.dependencies.androidX
import com.arakim.googlecalendarclone.dependencies.compose
import com.arakim.googlecalendarclone.dependencies.hilt
import com.arakim.googlecalendarclone.dependencies.libs.AndroidX
import com.arakim.googlecalendarclone.dependencies.libs.Compose
import com.arakim.googlecalendarclone.dependencies.libs.Tools
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

        testInstrumentationRunner = "com.arakim.googlecalendarclone.util.androidtest.HiltTestRunner"
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
        isCoreLibraryDesugaringEnabled = true
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
    implementation(project(":data:calendar-setup"))
    implementation(project(":ui:main-navigation"))
    implementation(project(":ui:main-navigation:destination"))
    implementation(project(":util:compose"))
    implementation(project(":data:user-calendar:google"))
    implementation(project(":data:user-calendar:fake"))
    kaptAndroidTestRelease(project(":util:android-test"))

    implementation(AndroidX.Core.SplashScreen)

    androidX()
    compose()
    hilt()
    coreLibraryDesugaring(Tools.CoreLibraryDesugar)
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
}
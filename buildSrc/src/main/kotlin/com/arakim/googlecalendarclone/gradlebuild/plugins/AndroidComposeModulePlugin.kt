package com.arakim.googlecalendarclone.gradlebuild.plugins

import com.arakim.googlecalendarclone.dependencies.debugImplementation
import com.arakim.googlecalendarclone.dependencies.implementationProject
import com.arakim.googlecalendarclone.dependencies.libs.Compose
import com.arakim.googlecalendarclone.gradlebuild.androidLibrary
import org.gradle.api.Project

class AndroidComposeModulePlugin : AndroidModulePlugin() {

    override fun apply(project: Project) {
        super.apply(project)
        project.dependencies.debugImplementation(Compose.UiTestManifest)
        project.dependencies.implementationProject(":ui:main-navigation:destination")
        project.androidLibrary().apply {
            composeOptions {
                kotlinCompilerExtensionVersion = Compose.KotlinCompilerExtensionVersion
            }
            buildFeatures {
                compose = true
            }
        }
    }
}
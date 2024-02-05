package com.arakim.googlecalendarclone.gradlebuild

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun setUpKotlinCompile(project: Project) {
    project.tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = BuildJvm.JvmTarget.toString()
        }
    }
}

fun Project.androidLibrary(): LibraryExtension =
    extensions.getByType(LibraryExtension::class.java)

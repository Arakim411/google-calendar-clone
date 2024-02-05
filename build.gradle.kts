import com.arakim.googlecalendarclone.dependencies.libs.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("io.gitlab.arturbosch.detekt").version("1.23.3")
}

detekt {
    parallel = true
    allRules = true
    config = files("$rootDir/config/detekt/config.yml")
    baseline = file("$rootDir/config/detekt/baseline.xml")
    source = files(
        fileTree(rootDir).matching {
            include("**/src/*/java/**/*.kt")
            include("**/src/*/kotlin/**/*.kt")
            include("**/*.gradle.kts")
            exclude("buildSrc/**")
        }
    )
}

dependencies {
    detektPlugins(Detekt.Formatting)
}
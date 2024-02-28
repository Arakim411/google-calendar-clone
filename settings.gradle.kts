pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Google Calendar Clone"
include(":app")
include(":data")
include(":data:sign-in")
include(":data:sign-in:common")
include(":data:sign-in:fake")
include(":data:sign-in:google")
include(":domain")
include(":domain:user")
include(":domain:user:sign-in")
include(":ui:common")
include(":ui:main-navigation")
include(":ui:main-navigation:destination")
include(":ui:app-navigation-drawer")
include(":ui:app-navigation-drawer:core")
include(":ui:screen:home")
include(":ui:screen:sign-in")
include(":ui:screen:splash")
include(":util:android")
include(":util:android-test")
include(":util:compose")
include(":util:kotlin")
include(":util:mvi")
include(":util:test")

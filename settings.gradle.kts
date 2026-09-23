// NightWatch root Gradle settings.
// Declares repositories and includes every Gradle module in the project.
// Modules: app (monitor), contact (receiver), core:{model,common},
//          sensing, perception, fusion, transport.

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

rootProject.name = "NightWatch"

include(
    ":app",
    ":contact",
    ":core:model",
    ":core:common",
    ":sensing",
    ":perception",
    ":fusion",
    ":transport",
)
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/QuizU-SevastianBahynskyi/quizu-schema")
            credentials {
                username = "QuizU-SevastianBahynskyi"
                password = System.getenv("GITHUB_PACKAGE_ACCESS_TOKEN")
            }
        }
    }
}

rootProject.name = "quizu-android-client"
include(":app")

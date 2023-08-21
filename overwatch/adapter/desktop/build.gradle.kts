import org.jetbrains.compose.compose

plugins {
    commonPlugins()
    id("org.jetbrains.compose") version "1.1.1"
}

dependencies {
    commonDependencies()

    // project
    implementation(project(":overwatch:usecase"))

    // kotlin desktop
    implementation(compose.desktop.currentOs)
}

repositories {
    commonRepositories()
}
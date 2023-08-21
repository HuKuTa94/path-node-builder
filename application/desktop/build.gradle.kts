import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    commonPlugins()
    id("org.jetbrains.compose") version "1.1.1"
}

dependencies {
    commonDependencies()

    // project
    implementation(project(":common"))
    implementation(project(":calculator"))
    implementation(project(":overwatch:filter"))
    implementation(project(":overwatch:usecase"))
    implementation(project(":overwatch:adapter:desktop"))

    // kotlin desktop
    implementation(compose.desktop.currentOs)
}

repositories {
    commonRepositories()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

compose.desktop {
    application {
        mainClass = "com.hukuta94.pathnodebuilder.application.PathNodeBuilderDesktopApplicationKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "path-node-builder-desktop"
            packageVersion = "1.0.0"
        }
    }
}
plugins {
    commonPlugins()
}

dependencies {
    commonDependencies()

    // project
    implementation(project(":common"))
    implementation(project(":calculator"))
    implementation(project(":overwatch:filter"))
    implementation(project(":overwatch:usecase"))
    implementation(project(":overwatch:adapter:web"))

    // spring boot
    implementation(Libs.SpringBoot.starter_web)
    implementation(Libs.SpringBoot.starter_thymleaf)
}

repositories {
    commonRepositories()
}
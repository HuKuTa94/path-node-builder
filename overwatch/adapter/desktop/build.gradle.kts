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
}

repositories {
    commonRepositories()
}
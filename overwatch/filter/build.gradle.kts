plugins {
    commonPlugins()
}

dependencies {
    commonDependencies()

    // project
    implementation(project(":common"))
    implementation(project(":calculator"))
}

repositories {
    commonRepositories()
}
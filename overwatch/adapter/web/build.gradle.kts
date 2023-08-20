plugins {
    commonPlugins()
}

dependencies {
    commonDependencies()

    // project
    implementation(project(":common"))
    implementation(project(":overwatch:usecase"))

    // spring boot
    implementation(Libs.SpringBoot.starter_web)
}

repositories {
    commonRepositories()
}
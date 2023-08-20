subprojects {
    tasks {
        withType<Test> {
            useJUnitPlatform()

            // This is for logging and can be removed
            testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }
}
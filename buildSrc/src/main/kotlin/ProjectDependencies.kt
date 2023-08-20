import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.internal.impldep.junit.runner.Version.id
import org.gradle.kotlin.dsl.*
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.commonPlugins() {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
}

fun RepositoryHandler.commonRepositories() {
    mavenCentral()
}

fun DependencyHandler.commonDependencies() {
    add("implementation", Libs.Kotlin.jdk8)
    add("implementation", Libs.Kotlin.reflect)
    add("testImplementation", Libs.Kotlin.test)
    add("testCompileOnly", Libs.JUnit.api)
    add("testRuntimeOnly", Libs.JUnit.engine)
//    add("implementation", Libs.ArrowKt.core)
//    add("implementation", Libs.JMolecules.ddd)
//    add("implementation", Libs.JMolecules.onion_architecture)
//    add("testImplementation", Libs.ArrowKt.core)
//    add("testImplementation", Libs.Kotest.arrow)
//    add("testImplementation", Libs.Kotest.junit5)
//    add("testImplementation", Libs.JMolecules.archunit)
//    add("testFixturesImplementation", Libs.ArrowKt.core)
}

object Libs {
    object Kotlin {
        const val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect"
        const val test = "org.jetbrains.kotlin:kotlin-test"
    }
    object ArrowKt {
        const private val version = "1.1.5"
        const val core = "io.arrow-kt:arrow-core:$version"
    }
    object JUnit {
        const private val version = "5.9.1"
        const val api = "org.junit.jupiter:junit-jupiter-api:$version"
        const val engine = "org.junit.jupiter:junit-jupiter-engine:$version"
    }
    object Kotest {
        private const val junit5_version = "5.5.5"
        const val junit5 = "io.kotest:kotest-runner-junit5:$junit5_version"

        private const val arrow_version = "1.1.1"
        const val arrow = "io.kotest.extensions:kotest-assertions-arrow-jvm:$arrow_version"
    }
    object ArchUnit {
        private const val version = "1.0.1"
        const val junit5 = "com.tngtech.archunit:archunit-junit5:$version"
    }
    object SpringBoot {
        private const val version = "2.5.6"
        const val starter_test = "org.springframework.boot:spring-boot-starter-test:$version"
        const val starter_web = "org.springframework.boot:spring-boot-starter-web:${version}"
        const val starter_thymleaf = "org.springframework.boot:spring-boot-starter-thymeleaf:$version"
    }
    object Jackson {
        private const val version = "2.13.4"
        const val module_kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:$version"
    }
    object JMolecules {
        private const val version = "1.6.0"
        const val ddd = "org.jmolecules:jmolecules-ddd:$version"
        const val archunit = "org.jmolecules:jmolecules-archunit:$version"
        const val onion_architecture = "org.jmolecules:jmolecules-onion-architecture:$version"
    }
}
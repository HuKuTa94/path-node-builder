package com.hukuta94.pathnodebuilder.application

import com.hukuta94.pathnodebuilder.application.configuration.ApplicationConfiguration
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<ApplicationConfiguration>(*args)
}

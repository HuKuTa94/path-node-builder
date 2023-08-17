package com.hukuta94.pathnodebuilder.adapter.overwatch.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class PresentationController {

    @GetMapping
    fun indexPage(): String {
        return "index"
    }
}
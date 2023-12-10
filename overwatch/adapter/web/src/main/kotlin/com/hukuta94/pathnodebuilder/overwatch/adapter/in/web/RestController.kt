package com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.web

import com.hukuta94.pathnodebuilder.overwatch.application.port.ComputeDistanceMatrixPipeline
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestController @Autowired constructor(
    private val pipeline: ComputeDistanceMatrixPipeline
) {
    @PostMapping("/distance-matrix/")
    fun computeDistanceMatrix(@RequestBody inputData: String): ResponseEntity<String> {
        return try {
            val result = pipeline.execute(inputData)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
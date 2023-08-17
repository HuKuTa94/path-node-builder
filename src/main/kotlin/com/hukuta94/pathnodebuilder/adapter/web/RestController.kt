package com.hukuta94.pathnodebuilder.adapter.web

import com.hukuta94.pathnodebuilder.pipeline.ComputeDistanceMatrixForOverwatchPipeline
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestController @Autowired constructor(
    private val pipeline: ComputeDistanceMatrixForOverwatchPipeline
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
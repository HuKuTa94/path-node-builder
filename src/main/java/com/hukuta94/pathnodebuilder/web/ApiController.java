package com.hukuta94.pathnodebuilder.web;

import com.hukuta94.pathnodebuilder.service.DistanceMatrixService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController
{
    private final DistanceMatrixService distanceMatrixService;

    @PostMapping("/distance-matrix/")
    public ResponseEntity<String> computeDistanceMatrix(@RequestBody String inputData)
    {
        try {
            String result = distanceMatrixService.computeDistanceMatrix(inputData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package me.riazulislam.infinitecineplexbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<?> getHealthStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "API is working"));
    }
}

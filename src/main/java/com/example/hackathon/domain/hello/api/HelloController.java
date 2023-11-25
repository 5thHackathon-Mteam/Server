package com.example.hackathon.domain.hello.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HelloController {

    @GetMapping
    public ResponseEntity<Boolean> health() {
        return ResponseEntity.ok().body(true);
    }

    @PostMapping
    public ResponseEntity<String> health_post(@RequestBody String body) {
        return ResponseEntity.ok().body(body);
    }


}


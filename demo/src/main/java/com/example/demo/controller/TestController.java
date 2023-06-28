package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController() {
        return "Hello World!";
    }

    // path variable (localhost:8080/test/123)
    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
        return "Hello World with path variable" + id;
    }

    // request param (localhost:8080/test/requestParam?param=123)
    @GetMapping("/requestParam")
    public String testControllerWithRequestParam(@RequestParam(required = false) int param) {
        return "Hello World with request parma " + param;
    }

    // using input DTO
    @GetMapping("/requestBody")
    public String testControllerWithRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World with request body. ID: " + testRequestBodyDTO.getId() + " and Message: " + testRequestBodyDTO.getMessage();
    }
}

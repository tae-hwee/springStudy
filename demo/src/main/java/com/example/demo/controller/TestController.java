package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController() {
        return "Hello World!";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
        return "Hello World with path variable" + id;
    }

    @GetMapping("/requestParam")
    public String testControllerWithRequestParam(@RequestParam(required = false) int param) {
        return "Hello World with request parma " + param;
    }
}

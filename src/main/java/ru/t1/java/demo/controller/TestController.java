package ru.t1.java.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping( value = "/test-demo-exception")
    public String test(){
        throw new RuntimeException();
    }

    @GetMapping( value = "/test-demo-time")
    public String test1() throws InterruptedException {
        Thread.sleep(1000);
        throw new RuntimeException();
    }
}

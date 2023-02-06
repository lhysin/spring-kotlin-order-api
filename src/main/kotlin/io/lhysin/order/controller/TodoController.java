package io.lhysin.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.lhysin.order.exception.ChaosException;

@RestController
public class TodoController {

    @GetMapping("/")
    public String test() {
        String a ="asd";
        if(a.contains("a")) {
            throw new ChaosException("");
        }
        return "";
    }
}

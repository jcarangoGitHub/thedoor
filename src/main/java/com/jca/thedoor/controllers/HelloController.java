package com.jca.thedoor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello2")
    public String hello() {
        log.info("Executing hello world method from logger");
        // diferentes niveles de logger:
        // log.warn("Executing hello world method from logger");
        // log.error("Executing hello world method from logger");
        return "Hola mundo2";
    }

}

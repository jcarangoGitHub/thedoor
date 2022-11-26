package com.jca.thedoor.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class IndexController {

    @GetMapping("/login")
    public String index(HttpSession session) {
        //TODO validate logout
        session.invalidate();

        return "login...";
    }
}

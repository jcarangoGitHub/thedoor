package com.jca.thedoor.controllers;

import com.jca.thedoor.security.payload.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class IndexController {

    //This is logout entrypoint. For some reason the server set it with /login after user send /logout
    @GetMapping("/login")
    public ResponseEntity index(HttpSession session) {
        //TODO validate logout
        session.invalidate();
        return ResponseEntity.ok("logged out");
    }
}

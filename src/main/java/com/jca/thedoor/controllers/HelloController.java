package com.jca.thedoor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(HelloController.class);

    //https://www.baeldung.com/spring-security-authentication-mongodb
    //@RolesAllowed("ROLE_SUPER")//No funciona??
    @PreAuthorize("hasRole('ROLE_SUPER')")
    @GetMapping("/hello2")
    public String hello() {
        log.info("Executing hello world method from logger");
        // diferentes niveles de logger:
        // log.warn("Executing hello world method from logger");
        // log.error("Executing hello world method from logger");
        return "Hola mundo2";
        //https://campus.open-bootcamp.com/cursos/14/leccion/364
        //continuar viendo video
        //hacer lógica con roles
    }

}

package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.ExchangeRate;
import com.jca.thedoor.service.impl.ExchangerMongoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchanger")
public class ExchangerController {
    private final ExchangerMongoService exchangerService;

    public ExchangerController(ExchangerMongoService exchangerService) {
        this.exchangerService = exchangerService;
    }

    @PostMapping("/getRatesByDate")
    public ResponseEntity<List<ExchangeRate>> getRatesByDate(@RequestBody String token) {
        return null;
    }

    @PostMapping("/getCurrentRates")
    public ResponseEntity<ExchangeRate> getCurrentRates(@RequestBody String token) {
        return exchangerService.getCurrentRates(token);
    }
}

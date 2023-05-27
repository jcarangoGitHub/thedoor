package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.ExchangeRate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExchangerService {
    ResponseEntity<ExchangeRate> getCurrentRates(String token);
    List<ExchangeRate>  getRatesByDate(String token);
}

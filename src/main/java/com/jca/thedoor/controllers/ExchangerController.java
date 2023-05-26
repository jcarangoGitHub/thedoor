package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.ExchangeRate;
import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.proofofconcept.HelloController;
import com.jca.thedoor.repository.mongodb.ExchangerRepository;
import com.jca.thedoor.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/api/exchanger")
public class ExchangerController {

    private final Logger log = LoggerFactory.getLogger(ExchangerController.class);
    private final ExchangerRepository exchangerRepository;

    public ExchangerController(ExchangerRepository exchangerRepository) {
        this.exchangerRepository = exchangerRepository;
    }

    @PostMapping("/saveExchanger")
    public ResponseEntity<ExchangeRate> saveExchanger(@Valid  @RequestBody ExchangeRate exchangeRate) {
        try {
            ExchangeRate created = exchangerRepository.save(exchangeRate);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getFieldFromDuplicateKeyExceptionMessage(e.getMessage()));
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (UnexpectedTypeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getCurrentRates")
    public ResponseEntity<ExchangeRate> getCurrentRates(@RequestBody String token) {
        try {
            Optional<ExchangeRate> result = exchangerRepository.findFirstByOrderByTimestampDesc();
            if (result.isPresent()) {
                Instant now = Instant.now();
                LocalDateTime nowLocal = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
                LocalDateTime resultLocalTime = LocalDateTime.ofInstant(result.get().getTimestamp(), ZoneId.systemDefault());
                Duration duration = Duration.between(resultLocalTime, nowLocal);
                long minutesDifference = duration.toMinutes();

                return minutesDifference > 60 ? getExchangeRateAndSaveIt(token) : ResponseEntity.ok(result.get());
            }

            return getExchangeRateAndSaveIt(token);

        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (UnexpectedTypeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private ResponseEntity<ExchangeRate> getExchangeRateAndSaveIt(String token) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder apiUrlBuilder = new StringBuilder("https://openexchangerates.org/api/latest.json?app_id=");//TODO property
        apiUrlBuilder.append(token);
        String apiUrl = apiUrlBuilder.toString();
        ResponseEntity<ExchangeRate> response = restTemplate.getForEntity(apiUrl, ExchangeRate.class);
        ExchangeRate exchangeRate = response.getBody();
        try {
            ExchangeRate created = exchangerRepository.save(exchangeRate);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException exception) {
            log.warn(MessageUtil.getFieldFromDuplicateKeyExceptionMessage(exception.getMessage()));
            return ResponseEntity.ok(exchangeRate);
        }
    }
}

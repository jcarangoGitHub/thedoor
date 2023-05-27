package com.jca.thedoor.service.impl;

import com.jca.thedoor.controllers.ExchangerController;
import com.jca.thedoor.entity.mongodb.ExchangeRate;
import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.repository.mongodb.ExchangerRepository;
import com.jca.thedoor.service.ExchangerService;
import com.jca.thedoor.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangerMongoService implements ExchangerService {
    @Autowired
    private ExchangerRepository exchangerRepository;
    private final Logger log = LoggerFactory.getLogger(ExchangerController.class);

    @Value("${app.openexchangerates.url.latest}")
    private String urlLatest;

    @Override
    public ResponseEntity<ExchangeRate> getCurrentRates(String token) {
        try {
            Instant now = Instant.now();
            LocalDateTime nowLocal = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
            Optional<ExchangeRate> result = exchangerRepository.findFirstByOrderByTimestampDesc();
            if (result.isPresent()) {
                LocalDateTime resultLocalTime = LocalDateTime.ofInstant(result.get().getTimestamp(), ZoneId.systemDefault());
                Duration duration = Duration.between(resultLocalTime, nowLocal);
                long minutesDifference = duration.toMinutes();

                return minutesDifference > 20 ? getExchangeRateAndSaveIt(token, nowLocal) : ResponseEntity.ok(result.get());
            }

            return getExchangeRateAndSaveIt(token, nowLocal);

        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (UnexpectedTypeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private ResponseEntity<ExchangeRate> getExchangeRateAndSaveIt(String token, LocalDateTime nowLocal) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder apiUrlBuilder = new StringBuilder(urlLatest);
        apiUrlBuilder.append(token);
        String apiUrl = apiUrlBuilder.toString();
        ResponseEntity<ExchangeRate> response = restTemplate.getForEntity(apiUrl, ExchangeRate.class);
        ExchangeRate exchangeRate = response.getBody();
        exchangeRate.setLocalDate(nowLocal.toString());
        try {
            ExchangeRate created = exchangerRepository.save(exchangeRate);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException exception) {
            log.warn(MessageUtil.getFieldFromDuplicateKeyExceptionMessage(exception.getMessage()));
            return ResponseEntity.ok(exchangeRate);
        }
    }

    @Override
    public List<ExchangeRate> getRatesByDate(String token) {
        Pageable pageable = PageRequest.of(0, 1);
        //exchangerRepository.findLastOfTheDay()
        // TODO form today, how get the first and last minute of the day
        //TODO from today query for each day. Either find add it to return list. Else call openexchange api and save it
        return null;
    }
}

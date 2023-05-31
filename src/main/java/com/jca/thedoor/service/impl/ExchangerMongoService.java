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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangerMongoService implements ExchangerService {
    @Autowired
    private ExchangerRepository exchangerRepository;
    private final Logger log = LoggerFactory.getLogger(ExchangerController.class);
    private final static int _colombiaGMT = -5;
    private final static int _lastDays = 7;

    @Value("${app.openexchangerates.url.latest}")
    private String urlLatest;
    @Value("${app.oenexchangerates.url.historical}")
    private String urlHistorical;

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

                return minutesDifference > 20 ? getExchangeRateAndSaveIt(urlLatest, new String[]{token}) : ResponseEntity.ok(result.get());//TODO back to 60
            }

            return getExchangeRateAndSaveIt(urlLatest, new String[]{token});

        } catch (ConstraintViolationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (UnexpectedTypeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private ResponseEntity<ExchangeRate> getExchangeRateAndSaveIt(String url, String[] params) {
        RestTemplate restTemplate = new RestTemplate();
        int paramsLength = params.length;
        if (paramsLength > 0) {
            String strCounter;
            for (int i=0; i<paramsLength; i++) {
                strCounter = "{"+String.valueOf(i)+"}";
                if (url.indexOf(strCounter) != -1) {
                    url = url.replace(strCounter, params[i]);
                }
            }
        }
        StringBuilder apiUrlBuilder = new StringBuilder(url);
        String apiUrl = apiUrlBuilder.toString();
        ResponseEntity<ExchangeRate> response = restTemplate.getForEntity(apiUrl, ExchangeRate.class);
        ExchangeRate exchangeRate = response.getBody();
        exchangeRate.setLocalDate(getGMTAdjustedByHours(exchangeRate.getTimestamp(), _colombiaGMT));
        try {
            ExchangeRate created = exchangerRepository.save(exchangeRate);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException exception) {
            log.warn(MessageUtil.getFieldFromDuplicateKeyExceptionMessage(exception.getMessage()));
            return ResponseEntity.ok(exchangeRate);
        }
    }

    private Instant getGMTAdjustedByHours(Instant timestamp, int hours) {
        ZoneOffset offset = ZoneOffset.ofHours(hours);

        return timestamp.atOffset(offset).toInstant();
    }

    @Override
    public ResponseEntity<List<ExchangeRate>> getRatesLastDays(String token) {
        LocalDate now = LocalDate.now();
        LocalDate past;
        LocalDateTime startDateTime, endDateTime;
        List<ExchangeRate> response = new ArrayList<>();

        for (int i=1; i <= _lastDays; i++) {
            past = now.minusDays(i);
            startDateTime = LocalDateTime.of(past, LocalTime.MIN);
            endDateTime = LocalDateTime.of(past, LocalTime.MAX);

            // Convert the dates to milliseconds
            long startDate = startDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            long endDate = endDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

            try {
                Optional<ExchangeRate> result = exchangerRepository.findFirstByTimestampBetweenOrderByTimestampDesc(Instant.ofEpochMilli(startDate),
                        Instant.ofEpochMilli(endDate));
                if (result.isPresent()) {
                    response.add(result.get());
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String pastFormated = past.format(formatter);
                    ResponseEntity<ExchangeRate> responseEntity = getExchangeRateAndSaveIt(urlHistorical, new String[]{pastFormated, token});
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        response.add(responseEntity.getBody());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(response);
    }
}

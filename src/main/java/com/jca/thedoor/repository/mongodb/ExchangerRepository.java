package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.ExchangeRate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangerRepository extends MongoRepository<ExchangeRate, String> {
    List<ExchangeRate> findByTimestamp(long timestamp);

    Optional<ExchangeRate> findFirstByOrderByTimestampDesc();

}

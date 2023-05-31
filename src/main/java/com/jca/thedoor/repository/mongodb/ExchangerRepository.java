package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.ExchangeRate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ExchangerRepository extends MongoRepository<ExchangeRate, String> {
    List<ExchangeRate> findByTimestamp(long timestamp);

    Optional<ExchangeRate> findFirstByOrderByTimestampDesc();

    Optional<ExchangeRate> findFirstByTimestampBetweenOrderByTimestampDesc(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

}

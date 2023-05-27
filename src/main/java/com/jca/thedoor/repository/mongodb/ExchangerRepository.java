package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.ExchangeRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExchangerRepository extends MongoRepository<ExchangeRate, String> {
    List<ExchangeRate> findByTimestamp(long timestamp);

    Optional<ExchangeRate> findFirstByOrderByTimestampDesc();

    @Query(value = "{ 'timestamp': { '$lt': new Date(:endDate), '$gte': new Date(:startDate) } }",
            sort = "{ 'timestamp': -1 }")
    List<ExchangeRate> findLastOfTheDay(@Param("startDate") long startDate, @Param("endDate") long endDate, Pageable pageable);

}

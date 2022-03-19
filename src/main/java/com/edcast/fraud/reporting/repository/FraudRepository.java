package com.edcast.fraud.reporting.repository;

import com.edcast.fraud.reporting.entity.Fraud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FraudRepository extends JpaRepository<Fraud, Integer> {

    Page<Fraud> findAll(Pageable pageable);


    @Query(value = "SELECT *\n" +
            "    FROM frauds\n" +
            "    WHERE date BETWEEN :from AND :to", nativeQuery = true)
    Page<Fraud> findAllBetweenDates(Pageable pageable, String from, String to);

}

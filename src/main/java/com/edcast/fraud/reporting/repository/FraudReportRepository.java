package com.edcast.fraud.reporting.repository;

import com.edcast.fraud.reporting.dto.FraudCasesPerCountry;
import com.edcast.fraud.reporting.entity.FraudReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FraudReportRepository extends JpaRepository<FraudReport,Integer> {
    @Query(value = "SELECT *\n" +
            "    FROM fraud_reports\n" +
            "    WHERE company = :company AND from_date = :from AND to_date = :to", nativeQuery = true)
    FraudReport findReport(String company, String from, String to);

    @Query(value="select country ,count(*) as total\n" +
            "from frauds\n" +
            "where date BETWEEN :from AND :to and company=:company \n" +
            "group by country",nativeQuery = true)
    List<FraudCasesPerCountry> findTotalFraudsBasedOnCompanyGroupedByCountry(String company, String from, String to);


}

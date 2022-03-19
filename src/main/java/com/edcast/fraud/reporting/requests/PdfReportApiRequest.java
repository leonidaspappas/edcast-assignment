package com.edcast.fraud.reporting.requests;

import com.edcast.fraud.reporting.dto.CompanyDto;
import com.edcast.fraud.reporting.dto.FraudCasesPerCountryPercent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfReportApiRequest {
    private CompanyDto company;
    private List<FraudCasesPerCountryPercent> records;
    private String from;
    private String to;
    private int overall;

}

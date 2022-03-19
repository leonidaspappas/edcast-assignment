package com.edcast.fraud.reporting.dto;

import lombok.Data;

@Data
public class FraudReportDto {
    String company;
    String from;
    String to;
}

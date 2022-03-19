package com.edcast.fraud.reporting.dto;

import lombok.Data;

@Data
public class FraudCasesPerCountryPercent {
    private String country;
    private int total;
    private double percentage;

    public FraudCasesPerCountryPercent(String country, int total) {
        this.country = country;
        this.total = total;
    }

    public void calculatePercentage(int overall){
        this.percentage = total / (double) overall;
    }

    @Override
    public String toString() {
        return "FraudGroupOfTotals{" +
                "country='" + country + '\'' +
                ", total=" + total +
                ", percentage=" + percentage +
                '}';
    }
}

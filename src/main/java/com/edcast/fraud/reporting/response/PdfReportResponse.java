package com.edcast.fraud.reporting.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfReportResponse {
    private int id;
    private String file;
}

package com.edcast.fraud.reporting.response;

import com.edcast.fraud.reporting.dto.PdfServiceDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfServiceResponse {
    PdfServiceDataDto data;
}

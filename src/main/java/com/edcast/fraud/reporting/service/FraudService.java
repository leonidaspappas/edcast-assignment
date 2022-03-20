package com.edcast.fraud.reporting.service;

import com.edcast.fraud.reporting.dto.CompanyDto;
import com.edcast.fraud.reporting.dto.FraudCasesPerCountry;
import com.edcast.fraud.reporting.dto.FraudCasesPerCountryPercent;
import com.edcast.fraud.reporting.entity.Fraud;
import com.edcast.fraud.reporting.entity.FraudReport;
import com.edcast.fraud.reporting.repository.FraudReportRepository;
import com.edcast.fraud.reporting.repository.FraudRepository;
import com.edcast.fraud.reporting.requests.PdfReportApiRequest;
import com.edcast.fraud.reporting.response.PdfReportResponse;
import com.edcast.fraud.reporting.response.PdfServiceResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.edcast.fraud.reporting.utils.DateTransformUtils.reformatDate;
import static com.edcast.fraud.reporting.utils.DateTransformUtils.transformStrDateToLocalDateTime;

@Service
public class FraudService {
    @Autowired
    private FraudRepository fraudRepository;
    @Autowired
    private FraudReportRepository fraudReportRepository;

    @Value("${edCast-pdf-report-uri}")
    private String edCastPdfReportUri ;

    public Fraud saveFraud(Fraud fraud) {
        Fraud saved = fraudRepository.save(fraud);
        return saved;
    }

    public List<Fraud> findAll(int page, int size) {
        Page<Fraud> pageFraud;
        Pageable paging = PageRequest.of(page, size);
        pageFraud = fraudRepository.findAll(paging);
        return pageFraud.getContent();

    }

    public List<Fraud> findAllBetweenDates(int page, int size, String from, String to) throws ParseException {
        Page<Fraud> pageFraud;
        Pageable paging = PageRequest.of(page, size);
        reformatDate(from);
        reformatDate(to);
        pageFraud = fraudRepository.findAllBetweenDates(paging, from, to);
        return pageFraud.getContent();
    }

    public PdfReportResponse generateFraudReport(String company, String fromDate, String toDate) throws ParseException {
        fromDate = reformatDate(fromDate);
        toDate = reformatDate(toDate);
        //find if report already exists
        FraudReport fraudReport = fraudReportRepository.findReport(company, fromDate, toDate);
        if (fraudReport != null) {
            PdfReportResponse pdfReportResponse = new PdfReportResponse(fraudReport.getId(), fraudReport.getReportBase64());
            return pdfReportResponse;
        }//todo  method generate
        //find the total frauds per country for certain company
        List<FraudCasesPerCountry> fraudGroupsOfTotal = fraudReportRepository.findTotalFraudsBasedOnCompanyGroupedByCountry(company, fromDate, toDate);
        int overallFraudsNum = fraudGroupsOfTotal.stream().mapToInt(FraudCasesPerCountry::getTotal).sum();
        List<FraudCasesPerCountryPercent> list = new ArrayList<>();
        fraudGroupsOfTotal.forEach(e -> {
            FraudCasesPerCountryPercent fraudCasesPerCountryPercent = new FraudCasesPerCountryPercent(e.getCountry(), e.getTotal());
            fraudCasesPerCountryPercent.calculatePercentage(overallFraudsNum);
            list.add(fraudCasesPerCountryPercent);
        });
        //call edcast api for pdf generation
        PdfReportApiRequest pdfReportApiRequest = new PdfReportApiRequest(new CompanyDto(company), list, fromDate, toDate, overallFraudsNum);
        final String uri = edCastPdfReportUri;
        //todo inject
        RestTemplate restTemplate = new RestTemplate();
        PdfServiceResponse resultPdfService = restTemplate.postForObject(uri, pdfReportApiRequest, PdfServiceResponse.class);
        //save of report in mysql
        LocalDateTime from_datetime = transformStrDateToLocalDateTime(fromDate);
        LocalDateTime to_datetime = transformStrDateToLocalDateTime(toDate);
        fraudReport = new FraudReport(resultPdfService.getData().getFile(), company, from_datetime, to_datetime);
        FraudReport saved = fraudReportRepository.save(fraudReport);
        //response creation
        PdfReportResponse pdfReportResponse = new PdfReportResponse(saved.getId(), saved.getReportBase64());
        return pdfReportResponse;
    }

    public Optional<FraudReport> findById(int id){
        Optional<FraudReport> fraudReport = fraudReportRepository.findById(id);
        return fraudReport;
    }

}

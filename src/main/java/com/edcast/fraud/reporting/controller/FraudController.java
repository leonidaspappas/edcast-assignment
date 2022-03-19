package com.edcast.fraud.reporting.controller;

import com.edcast.fraud.reporting.dto.FraudReportDto;
import com.edcast.fraud.reporting.entity.Fraud;
import com.edcast.fraud.reporting.entity.FraudReport;
import com.edcast.fraud.reporting.response.PdfReportResponse;
import com.edcast.fraud.reporting.service.FraudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class FraudController {

    @Autowired
    private FraudService fraudService;

    @PostMapping("/fraud")
    public Fraud addFraud(@RequestBody Fraud fraud){
        return fraudService.saveFraud(fraud);
    }
    @GetMapping("/fraud/list")
    public List<Fraud> getAllTutorials( @RequestParam(name="page",defaultValue = "0") int page,
                                        @RequestParam(name="size",defaultValue = "3") int size,
                                        @RequestParam(name="from") String from,
                                        @RequestParam(name="to") String to) throws ParseException {
        return fraudService.findAllBetweenDates(page,size,from,to);
    }

    @PostMapping("/fraud/report/generate")
    public PdfReportResponse generateFraudReport(@RequestBody FraudReportDto fraudReportDto) throws ParseException {
        return fraudService.generateFraudReport(fraudReportDto.getCompany(), fraudReportDto.getFrom(), fraudReportDto.getTo());
    }
    @GetMapping("/fraud/report/{id}")
    public Optional<FraudReport> getFraudReportById(@PathVariable(name="id") int id){
        return fraudService.findById(id);
    }
}

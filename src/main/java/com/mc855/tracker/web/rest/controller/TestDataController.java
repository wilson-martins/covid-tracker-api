package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.service.TestDataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testData")
public class TestDataController {

    @Autowired
    private TestDataGeneratorService testDataGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateData(
            @RequestParam("persons") Integer persons,
            @RequestParam("locationsPerBatch") Integer locationsPerBatch,
            @RequestParam("locationsBatchesPerPerson") Integer locationsBatchesPerPerson) {

        this.testDataGeneratorService.generatePerson(persons);
        this.testDataGeneratorService.generateLocation(locationsPerBatch, locationsBatchesPerPerson);
        this.testDataGeneratorService.generateStatusHistory();

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

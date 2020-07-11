package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.domain.StatusHistory;
import com.mc855.tracker.service.StatusHistoryBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statusHistory")
public class StatusHistoryController {

    @Autowired
    private StatusHistoryBO statusHistoryBO;

    @GetMapping("")
    public ResponseEntity<List<StatusHistory>> getStatusHistoryList() {
        return new ResponseEntity(statusHistoryBO.getStatusHistoryList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusHistory> getStatusHistory(@PathVariable Long id) {
        return new ResponseEntity(statusHistoryBO.getStatusHistory(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStatusHistory(@PathVariable Long id) {
        statusHistoryBO.deleteStatusHistory(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<StatusHistory> updateStatusHistory(@RequestBody StatusHistory statusHistory) {
        return new ResponseEntity(statusHistoryBO.updateStatusHistory(statusHistory), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<StatusHistory> addPerson(@RequestBody StatusHistory statusHistory) {
        return new ResponseEntity(statusHistoryBO.updateStatusHistory(statusHistory), HttpStatus.CREATED);
    }
}

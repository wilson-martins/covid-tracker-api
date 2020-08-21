package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.repository.PersonRepository;
import com.mc855.tracker.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/{personId}")
    public ResponseEntity<String> register(@PathVariable("personId") Long personId) {
        try {
            this.notificationService.sendNotifications(Arrays.asList(this.personRepository.findById(personId).orElse(null)));
            log.info("Successfully executed /notification/");
        } catch (Exception e) {
            log.error("Error executing /notification/", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}

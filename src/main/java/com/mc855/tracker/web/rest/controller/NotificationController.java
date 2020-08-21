package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.service.NotificationService;
import com.mc855.tracker.service.dto.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    public NotificationService notificationService;
    @PutMapping("")
    public ResponseEntity<String> register(@RequestBody LocationDto locationDto) {
        try {
//            this.locationBO.register(locationDto);
            log.info("Successfully executed /location/register");
        } catch (Exception e) {
            log.error("Error executing /location/register", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}

package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.service.LocationBO;
import com.mc855.tracker.service.dto.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationBO locationBO;

    @PutMapping("")
    public ResponseEntity<String> register(@RequestBody LocationDto locationDto) {
        try {
            this.locationBO.register(locationDto);
            log.info("Successfully executed /location/register");
        } catch (Exception e) {
            log.error("Error executing /location/register", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/getByPersonId/{personId}")
    public ResponseEntity<List<LocationDto>> getByPersonId(@PathVariable Long personId) {
        List<LocationDto> locationDtos;
        try {
            locationDtos = this.locationBO.getByPersonId(personId);
        } catch (Exception e) {
            log.error("Error executing /location/register", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(locationDtos, HttpStatus.OK);
    }
}

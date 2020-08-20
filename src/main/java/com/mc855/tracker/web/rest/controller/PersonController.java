package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.service.PersonBO;
import com.mc855.tracker.domain.Person;
import com.mc855.tracker.service.dto.UpdateGoogleIdTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Slf4j
public class PersonController {

    @Autowired
    private PersonBO personBO;

    @GetMapping("")
    public ResponseEntity<List<Person>> getPersons() {
        List<Person> persons;
        try {
            persons = personBO.getPersons();
            log.info("Successfully executed /person");
        } catch (Exception e) {
            log.error("Error executing /person", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Person person;
        try {
            person = personBO.getPerson(id);
            log.info("Successfully executed get /person/{id}");
        } catch (Exception e) {
            log.error("Error executing /person/{id}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(person, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id) {
        try {
            personBO.deletePerson(id);
            log.info("Successfully executed delete /person/{id}");
        } catch (Exception e) {
            log.error("Error executing delete /person/{id}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person response;
        try {
            response = personBO.updatePerson(person);
            log.info("Successfully executed post /person/{id}");
        } catch (Exception e) {
            log.error("Error executing post /person/{id}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person response;
        try {
            response = personBO.updatePerson(person);
            log.info("Successfully executed put /person/{id}");
        } catch (Exception e) {
            log.error("Error executing put /person/{id}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/setGoogleId")
    public ResponseEntity<Person> setGoogleId(@RequestBody UpdateGoogleIdTokenDto googleIdDto) {
        try {
            personBO.setGoogleId(googleIdDto);
            log.info("Successfully executed /person/setGoogleIdToken");
        } catch (Exception e) {
            log.error("Error executing /person/setGoogleIdToken", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}

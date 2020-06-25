package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.service.PersonBO;
import com.mc855.tracker.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonBO personBO;

    @GetMapping("")
    public ResponseEntity<List<Person>> getPersons() {
        return new ResponseEntity(personBO.getPersons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return new ResponseEntity(personBO.getPerson(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id) {
        personBO.deletePerson(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        return new ResponseEntity(personBO.updatePerson(person), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return new ResponseEntity(personBO.updatePerson(person), HttpStatus.CREATED);
    }
}

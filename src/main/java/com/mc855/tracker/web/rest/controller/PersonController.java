package com.mc855.tracker.web.rest.controller;

import com.mc855.tracker.service.PersonBO;
import com.mc855.tracker.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonBO personBO;

    @GetMapping("")
    public List<Person> getPersons() {
        return personBO.getPersons();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personBO.getPerson(id);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personBO.deletePerson(id);
    }

    @PostMapping("")
    public Person updatePerson(@RequestBody Person person) {
        return personBO.updatePerson(person);
    }
}

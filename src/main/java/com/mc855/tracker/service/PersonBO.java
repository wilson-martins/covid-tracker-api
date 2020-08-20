package com.mc855.tracker.service;

import com.mc855.tracker.domain.Person;
import com.mc855.tracker.repository.PersonRepository;
import com.mc855.tracker.service.dto.UpdateGoogleIdTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PersonBO {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Collection<Person> getPersons(Collection<Long> personsIds) {
        if (personsIds == null || personsIds.isEmpty()) {
            return new ArrayList<>();
        }
        return personRepository.findAllByPersonIdIn(personsIds);
    }

    public Person getPerson(Long id) {
        Optional<Person> person = personRepository.findById(id);

        return person.orElse(null);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    public Boolean setGoogleId(UpdateGoogleIdTokenDto updateGoogleIdTokenDto) {
        if (updateGoogleIdTokenDto == null || StringUtils.isEmpty(updateGoogleIdTokenDto.getGoogleIdToken()) || StringUtils.isEmpty(updateGoogleIdTokenDto.getPersonId())) {
            throw new IllegalArgumentException("PersonId and Google id token must not be empty");
        }

        int r = personRepository.setGoogleIdToken(updateGoogleIdTokenDto.getGoogleIdToken(), updateGoogleIdTokenDto.getPersonId());

        return r > 0;
    }
}

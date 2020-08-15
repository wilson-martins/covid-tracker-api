package com.mc855.tracker.service;

import com.mc855.tracker.domain.Location;
import com.mc855.tracker.domain.Person;
import com.mc855.tracker.domain.StatusHistory;
import com.mc855.tracker.domain.reference.HealthState;
import com.mc855.tracker.repository.LocationRepository;
import com.mc855.tracker.service.dto.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationBO {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PersonBO personBO;

    @Autowired
    private StatusHistoryBO statusHistoryBO;

    public void register(LocationDto locationDto) {
        if (locationDto == null) {
            log.info("Could not register Location. Arguement NULL.");
            return;
        }

        if (locationDto.getPersonId() != null) {
            Person person = this.personBO.getPerson(locationDto.getPersonId());
            if (person == null) {
                log.info("Could not find Person with id [{}]", locationDto.getPersonId());
                throw new ServiceException("Invalid Person ID");
            }
        }

        Location location = new Location(locationDto);
        try {
            this.locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            log.warn("Date+Person+Lat+Long key already stored [{}+{}+{}+{}]", location.getVisitedDt(), location.getPersonId(), location.getShortLatitude(), location.getShortLongitude());
        } catch (Exception e) {
            log.error("Error trying to save Location", e);
        }
    }

    public List<LocationDto> getByPersonId(Long personId) {
        if (personId == null) {
            return new ArrayList<>();
        }
        return this.locationRepository.findAllByPersonId(personId).stream().map(LocationDto::new).collect(Collectors.toList());
    }

    public Collection<Person> checkRisk(Collection<Long> personIds, Collection<HealthState> healthStates) {
        if (personIds == null || personIds.isEmpty()) {
            personIds = Collections.singletonList(-1L);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -12);
        Collection<Location> locations = this.locationRepository.findRiskLocations(calendar.getTime(), personIds);
        Map<String, List<Location>> keyLocationsMap = locations.stream().collect(Collectors.groupingBy(Location::getKey));
        Collection<Long> locationPersonsIds = new HashSet<>();
        keyLocationsMap.forEach((k, v) -> v.forEach(l -> locationPersonsIds.add(l.getPersonId())));
        Collection<Person> persons = this.personBO.getPersons(locationPersonsIds);
        Map<Long, Person> personMap = persons.stream().collect(Collectors.toMap(Person::getPersonId, p -> p));

        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        Collection<StatusHistory> statusHistories = this.statusHistoryBO.getStatusHistory(
                persons.stream().map(Person::getPersonId).collect(Collectors.toList()),
                healthStates, calendar.getTime());
        Map<Long, StatusHistory> personStatusMap = statusHistories.stream().collect(Collectors.toMap(StatusHistory::getPersonId, x -> x));

        Map<String, Set<Person>> keyPersonsMap = new HashMap<>();
        for (Map.Entry<String, List<Location>> keyValue : keyLocationsMap.entrySet()) {
            Set<Person> personList = keyPersonsMap.get(keyValue.getKey());
            if (personList == null) {
                personList = new HashSet<>();
            }

            boolean contaminated = false;

            for (Location l : keyValue.getValue()) {
                personList.add(personMap.get(l.getPersonId()));

                StatusHistory statusHistory = personStatusMap.get(l.getPersonId());
                if (statusHistory != null) {
                    contaminated = true;
                }
            }

            if (contaminated) {
                keyPersonsMap.put(keyValue.getKey(), personList);
            }
        }

        Collection<Person> peopleInRisk = new ArrayList<>();
        for (Set<Person> personSet : keyPersonsMap.values()) {
            for (Person person : personSet) {
                StatusHistory statusHistory = personStatusMap.get(person.getPersonId());
                if (statusHistory == null) {
                    peopleInRisk.add(person);
                }
            }
        }

        return peopleInRisk;
    }
}

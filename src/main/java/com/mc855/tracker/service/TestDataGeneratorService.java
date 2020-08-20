package com.mc855.tracker.service;

import com.mc855.tracker.domain.Location;
import com.mc855.tracker.domain.Person;
import com.mc855.tracker.domain.StatusHistory;
import com.mc855.tracker.domain.reference.HealthState;
import com.mc855.tracker.repository.LocationRepository;
import com.mc855.tracker.repository.PersonRepository;
import com.mc855.tracker.repository.StatusHistoryRepository;
import com.mc855.tracker.service.dto.StatusHistoryTestDataDto;
import com.mc855.tracker.service.dto.TestDataDto;
import com.mc855.tracker.util.CsvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestDataGeneratorService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    private static List<TestDataDto> testDataList = new ArrayList<>();
    private static Map<Integer, List<HealthState>> healthStateMap = new HashMap<>();

    public void generatePerson(Integer generatePerson) {
        if (generatePerson == null) {
            return;
        }

        if (testDataList == null || testDataList.isEmpty()) {
            populateTestData();
        }

        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < generatePerson; i++) {
            log.info("Generating person [{}/{}]", i + 1, generatePerson);
            Person person = new Person();
            person.setFirstName(testDataList.get((int) Math.round((Math.random() * (testDataList.size() - 1)))).getFirstName());
            person.setMiddleName(testDataList.get((int) Math.round((Math.random() * (testDataList.size() - 1)))).getMiddleName());
            person.setLastName(testDataList.get((int) Math.round((Math.random() * (testDataList.size() - 1)))).getLastName());
            person.setEmailAddr(testDataList.get((int) Math.round((Math.random() * (testDataList.size() - 1)))).getEmail());
            person.setPhoneNum(String.valueOf(BigDecimal.valueOf((Math.random() * (999999999 - 900000000)) + 900000000).intValue()));
            person.setBirthDay((int) Math.round((Math.random() * (28 - 1)) + 1));
            person.setBithMonth((int) Math.round((Math.random() * (12 - 1)) + 1));
            person.setBirthYear((int) Math.round((Math.random() * (2010 - 1940)) + 1940));
            persons.add(person);
        }

        log.info("Saving generated [{}] persons", persons.size());
        this.personRepository.saveAll(persons);
    }

    public void generateLocation(Integer entriesPerBatch, Integer batchesPerPerson) {

        if (entriesPerBatch == null || batchesPerPerson == null) {
            entriesPerBatch = 120;
            batchesPerPerson = 40;
        }

        final List<Person> persons = this.personRepository.findAll();

        Collection<Location> locations = new ArrayList<>();
        for (Person person : persons) {
            log.info("Generating locations for person [{}/{}]", persons.indexOf(person) + 1, persons.size());
            for (int j = 0; j < batchesPerPerson; j++) {
                double baseLatitude = -1 * ((Math.random() * (23053654 - 22835610)) + 22835610) / 1000000;
                double baseLongitude = -1 * ((Math.random() * (47236467 - 46963849)) + 46963849) / 1000000;
                long baseTime = BigDecimal.valueOf((Math.random() * (1597723605000L - 1595045205000L)) + 1595045205000L).longValue();
                for (int i = 0; i < entriesPerBatch; i++) {
                    Location location = new Location();
                    location.setPersonId(person.getPersonId());
                    int direction = Math.random() * 10 >= 5 ? 1 : -1;
                    location.setLatitude(baseLatitude);
                    double latVariation = direction * (Math.random() * 400) / 100000;
                    baseLatitude = baseLatitude + latVariation;
                    location.setShortLat();
                    direction = Math.random() * 10 >= 5 ? 1 : -1;
                    location.setLongitude(baseLongitude);
                    double longVariation = direction * (Math.random() * 400) / 100000;
                    baseLongitude = baseLongitude + longVariation;
                    location.setShortLong();
                    location.setVisitedDt(new Date(baseTime));
                    baseTime = baseTime + 60000;
                    locations.add(location);
                }
            }

            log.info("Saving [{}] generated locations", locations.size());
            this.locationRepository.saveAll(locations);
            locations.clear();
        }


    }

    public void generateStatusHistory() {

        if (healthStateMap == null || healthStateMap.isEmpty()) {
            populateStatusHistoryTestData();
        }
        final List<Long> personIds = this.personRepository.findAllPersonIds();

        Collection<StatusHistory> statusHistories = new ArrayList<>();
        for (Long personId : personIds) {
            log.info("Generating status histories for person [{}/{}]", personIds.indexOf(personId) + 1, personIds.size());
            if (0.3 >= Math.random()) {
                continue;
            }

            long baseTime = BigDecimal.valueOf((Math.random() * (1597723605000L - 1595045205000L)) + 1595045205000L).longValue();
            int statusCase = BigDecimal.valueOf(Math.random() * healthStateMap.keySet().size() - 1).intValue();
            List<HealthState> healthStates = healthStateMap.get(statusCase);

            for (HealthState healthState : healthStates) {
                StatusHistory statusHistory = new StatusHistory();
                statusHistory.setDiseaseId(1L);
                statusHistory.setStatusDt(new Date(baseTime));
                long timeDelta = BigDecimal.valueOf(Math.random() * 1123200000).intValue();
                baseTime += timeDelta;
                statusHistory.setValue(healthState);
                statusHistory.setPersonId(personId);
                statusHistories.add(statusHistory);
            }
        }

        log.info("Saving [{}] generated status histories", statusHistories.size());
        this.statusHistoryRepository.saveAll(statusHistories);
    }

    private void populateTestData() {
        ClassLoader classLoader = getClass().getClassLoader();

        URL fileUrl = classLoader.getResource("random_data_generator.csv");
        if (fileUrl != null) {
            testDataList = this.populateTestData(fileUrl.getPath(), TestDataDto.class);
        }
    }

    private void populateStatusHistoryTestData() {
        ClassLoader classLoader = getClass().getClassLoader();

        URL fileUrl = classLoader.getResource("status_history_possible_cases.csv");
        if (fileUrl != null) {
            List<StatusHistoryTestDataDto> testDataDtos = this.populateTestData(fileUrl.getPath(), StatusHistoryTestDataDto.class);

            if (testDataDtos.isEmpty()) {
                return;
            }

            Map<Integer, List<StatusHistoryTestDataDto>> dtoMap = testDataDtos.stream().collect(Collectors.groupingBy(StatusHistoryTestDataDto::getCaseGroup));
            for (Map.Entry<Integer, List<StatusHistoryTestDataDto>> entry : dtoMap.entrySet()) {
                healthStateMap.put(entry.getKey(),
                        entry.getValue().stream().sorted(Comparator.comparing(StatusHistoryTestDataDto::getPosition)).map(x -> HealthState.valueOf(x.getState())).collect(Collectors.toList()));
            }
        }
    }

    private <T> List<T> populateTestData(String filePath, Class<T> clazz) {
        if (filePath == null || filePath.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> testDataDtos = CsvUtil.getObject(clazz, filePath);
        if (testDataDtos == null || testDataDtos.isEmpty()) {
            log.error("Could not populate data test");
            return new ArrayList<>();
        }

        return testDataDtos;
    }
}

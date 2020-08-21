package com.mc855.tracker.jobs;

import com.mc855.tracker.domain.Person;
import com.mc855.tracker.domain.reference.HealthState;
import com.mc855.tracker.service.LocationBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
public class ScheduleTasks {

    @Autowired
    private LocationBO locationBO;

    @Scheduled(fixedDelay = 1000)
    public void checkRisks() {
        HealthState[] healthStates = {HealthState.POSITIVE, HealthState.CURED};
        Collection<Person> persons = this.locationBO.checkRisk(null, Arrays.asList(healthStates));
        log.info("Found [{}] persons to notify", persons.size());
    }
}

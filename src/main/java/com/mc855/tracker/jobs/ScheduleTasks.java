package com.mc855.tracker.jobs;

import com.mc855.tracker.service.LocationBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleTasks {

    @Autowired
    private LocationBO locationBO;

    @Scheduled(fixedRateString = "1000")
    public void checkRisks() {
//        this.locationBO.checkRisk();
    }
}

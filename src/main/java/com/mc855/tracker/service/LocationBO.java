package com.mc855.tracker.service;

import com.mc855.tracker.domain.Location;
import com.mc855.tracker.domain.Person;
import com.mc855.tracker.repository.LocationRepository;
import com.mc855.tracker.service.dto.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationBO {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PersonBO personBO;

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

        Location location = this.locationRepository.findByPersonIdAndLatitudeAndLongitude(
                locationDto.getPersonId(), locationDto.getLatitude(), locationDto.getLongitude()
        );
        if (location == null) {
            location = new Location();
            location.setPersonId(locationDto.getPersonId());
            location.setLatitude(locationDto.getLatitude());
            location.setLongitude(locationDto.getLongitude());
        } else {
            location.setLastVisit(new Date());
            location.setVisitCounter(location.getVisitCounter() + 1);
        }

        this.locationRepository.save(location);
    }

    public List<LocationDto> getByPersonId(Long personId) {
        if (personId == null) {
            return new ArrayList<>();
        }
        return this.locationRepository.findAllByPersonId(personId).stream().map(LocationDto::new).collect(Collectors.toList());
    }
}

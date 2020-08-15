package com.mc855.tracker.service.dto;

import com.mc855.tracker.domain.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LocationDto {

    private Long personId;
    private Double latitude;
    private Double longitude;
    private Date visitedDt;

    public LocationDto(Location location) {
        this.personId = location.getPersonId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.visitedDt = location.getVisitedDt();
    }
}

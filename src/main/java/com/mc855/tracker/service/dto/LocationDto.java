package com.mc855.tracker.service.dto;

import com.mc855.tracker.domain.Location;
import lombok.Data;

@Data
public class LocationDto {

    private Long personId;
    private Double latitude;
    private Double longitude;

    public LocationDto(Location location) {
        this.personId = location.getPersonId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}

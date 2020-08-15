package com.mc855.tracker.domain;

import com.mc855.tracker.domain.auditable.AuditFields;
import com.mc855.tracker.domain.auditable.AuditInterceptor;
import com.mc855.tracker.domain.auditable.Auditable;
import com.mc855.tracker.service.dto.LocationDto;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditInterceptor.class)
@Table(name = "location")
public class Location implements Serializable, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "short_latitude")
    private Double shortLatitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "short_longitude")
    private Double shortLongitude;

    @Column(name = "visited_dt")
    private Date visitedDt;

    @Embedded
    private AuditFields audit = new AuditFields();

    public Location(LocationDto dto) {
        this.personId = dto.getPersonId();
        this.latitude = dto.getLatitude();
        this.shortLatitude = BigDecimal.valueOf(dto.getLatitude()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.longitude = dto.getLongitude();
        this.shortLongitude = BigDecimal.valueOf(dto.getLongitude()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.visitedDt = dto.getVisitedDt();
    }

    public String getKey() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.visitedDt);

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(shortLatitude)
                .append(shortLongitude)
                .append(cal.get(Calendar.YEAR))
                .append(cal.get(Calendar.MONTH))
                .append(cal.get(Calendar.DAY_OF_MONTH))
                .append(cal.get(Calendar.HOUR));

        return strBuilder.toString();
    }
}
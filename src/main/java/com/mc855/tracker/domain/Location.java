package com.mc855.tracker.domain;

import com.mc855.tracker.domain.auditable.AuditFields;
import com.mc855.tracker.domain.auditable.Auditable;
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
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditFields.class)
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

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "last_visit")
    private Date lastVisit = new Date();

    @Column(name = "visit_counter")
    private Integer visitCounter = 1;

    @Embedded
    private AuditFields audit = new AuditFields();
}
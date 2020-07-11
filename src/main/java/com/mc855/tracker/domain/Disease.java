package com.mc855.tracker.domain;

import com.mc855.tracker.domain.auditable.AuditFields;
import com.mc855.tracker.domain.auditable.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data

@Entity
@EntityListeners(AuditFields.class)
@Table(name = "disease")
public class Disease implements Serializable, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id")
    private Long personId;

    @Column(name = "name")
    private String name;

    @Column(name = "incubation_period")
    private Integer incubationPeriod;

    @Column(name = "total_period")
    private Integer totalPeriod;

    @Embedded
    private AuditFields audit = new AuditFields();
}

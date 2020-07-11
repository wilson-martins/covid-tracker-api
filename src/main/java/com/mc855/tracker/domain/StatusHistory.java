package com.mc855.tracker.domain;

import com.mc855.tracker.domain.auditable.AuditFields;
import com.mc855.tracker.domain.auditable.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data

@Entity
@EntityListeners(AuditFields.class)
@Table(name = "status_history")
public class StatusHistory implements Serializable, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_history_id")
    private Long statusHistoryId;

    @Column(name = "disease_id")
    private Long diseaseId;

    @Column(name = "status_dt")
    private Date statusDt;

    @Column(name = "value")
    @Enumerated(EnumType.STRING)
    private HealthState value;

    @Column(name = "person_id")
    private Long personId;

    @Embedded
    private AuditFields audit = new AuditFields();
}

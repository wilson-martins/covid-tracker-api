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

@Data

@Entity
@EntityListeners(AuditFields.class)
@Table(name = "person")
public class Person implements Serializable, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_addr")
    private String emailAddr;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "birth_day")
    private Integer birthDay;

    @Column(name = "birth_month")
    private Integer bithMonth;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "google_id_token")
    private String googleIdToken;

    @Column(name = "google_profile_picture_url")
    private String googleProfilePictureUrl;

    @Embedded
    private AuditFields audit = new AuditFields();
}

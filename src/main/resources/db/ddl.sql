create table person
(
    person_id int auto_increment,
    first_name varchar(40) null,
    middle_name varchar(40) null,
    last_name varchar(40) null,
    email_addr varchar(80) null,
    phone_num varchar(40) null,
    birth_day int null,
    birth_month int null,
    birth_year int null,
    create_user varchar(40) null,
    create_dt datetime null,
    update_user varchar(40) null,
    update_dt datetime null,
    constraint person_pk
        primary key (person_id)
);

create table status_history
(
    status_history_id int auto_increment,
    disease_id int null,
    status_dt datetime null,
    value varchar(10) null,
    create_user varchar(40) null,
    create_dt datetime null,
    update_user varchar(40) null,
    update_dt datetime null,
    constraint status_history_pk
        primary key (status_history_id)
);

create table disease
(
    disease_id int auto_increment,
    name varchar(40) null,
    incubation_period int null,
    total_period int null,
    create_user varchar(40) null,
    create_dt datetime null,
    update_user varchar(40) null,
    update_dt datetime null,
    constraint disease_pk
        primary key (disease_id)
);

create table person_contact
(
    person_contact_id int auto_increment,
    person_1_id       int         null,
    person_2_id       int         null,
    date_start        int         null,
    date_end          int         null,
    proximity         int         null,
    create_user       varchar(40) null,
    create_dt         datetime    null,
    update_user       varchar(40) null,
    update_dt         datetime    null,
    constraint person_contact_pk
        primary key (person_contact_id)
);

create table address
(
    address_id  int auto_increment,
    person_id   int          null,
    addr_1      varchar(100) null,
    addr_2      varchar(100) null,
    city        varchar(100) null,
    state       varchar(100) null,
    country_cd  varchar(10)  null,
    zip_cd      varchar(20)  null,
    type_cd     varchar(10)  null,
    notes       varchar(100) null,
    create_user varchar(40)  null,
    create_dt   datetime     null,
    update_user varchar(40)  null,
    update_dt   datetime     null,
    constraint address_pk
        primary key (address_id)
);

create table person_location_config
(
    person_location_config_id int auto_increment,
    disease_id                int         null,
    threshold_distance        int         null,
    threshold_duration        int         null,
    create_user               varchar(40) null,
    create_dt                 datetime    null,
    update_user               varchar(40) null,
    update_dt                 datetime    null,
    constraint person_location_config_pk
        primary key (person_location_config_id)
);

ALTER TABLE person ADD COLUMN google_id VARCHAR (256);
ALTER TABLE person ADD COLUMN google_id_token VARCHAR (256);
ALTER TABLE person ADD COLUMN google_profile_picture_url VARCHAR (512);

ALTER TABLE status_history ADD COLUMN person_id bigint, ADD FOREIGN KEY fk_person(person_id) REFERENCES person(person_id);
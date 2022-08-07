create table RESTAURANT
(
    ID      BIGINT auto_increment
        primary key,
    NAME    CHARACTER VARYING,
    PEANUT  DOUBLE,
    EGG     DOUBLE,
    DAIRY   DOUBLE,
    ADDRESS CHARACTER VARYING,
    CITY    CHARACTER VARYING,
    STATE   CHARACTER VARYING,
    COUNTRY INTEGER,
    ZIPCODE INTEGER
);


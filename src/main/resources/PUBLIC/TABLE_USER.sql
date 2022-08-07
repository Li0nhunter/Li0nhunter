create table TABLE_USER
(
    ID             BIGINT auto_increment
        primary key,
    DISPLAY_NAME   CHARACTER VARYING,
    CITY           CHARACTER VARYING,
    ZIPCODE        INTEGER,
    EGG_ALLERGY    BOOLEAN,
    DAIRY_ALLERGY  BOOLEAN,
    PEANUT_ALLERGY BOOLEAN
);


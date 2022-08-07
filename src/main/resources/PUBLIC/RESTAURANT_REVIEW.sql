create table RESTAURANT_REVIEW
(
    ID         BIGINT auto_increment
        primary key,
    RESTAURANT BIGINT,
    SUBMITTER  CHARACTER VARYING,
    PEANUT     INTEGER,
    DAIRY      INTEGER,
    EGG        INTEGER,
    COMMENTARY CHARACTER VARYING,
    STATUS     CHARACTER VARYING
);


create table INFORMATION_SCHEMA.INDEX_COLUMNS
(
    INDEX_CATALOG          CHARACTER VARYING,
    INDEX_SCHEMA           CHARACTER VARYING,
    INDEX_NAME             CHARACTER VARYING,
    TABLE_CATALOG          CHARACTER VARYING,
    TABLE_SCHEMA           CHARACTER VARYING,
    TABLE_NAME             CHARACTER VARYING,
    COLUMN_NAME            CHARACTER VARYING,
    ORDINAL_POSITION       INTEGER,
    ORDERING_SPECIFICATION CHARACTER VARYING,
    NULL_ORDERING          CHARACTER VARYING,
    IS_UNIQUE              BOOLEAN
);


--liquibase formatted sql

--changeset ilyin:create-users-table context:prod
CREATE TABLE users
(
    id       BIGSERIAL
        CONSTRAINT users_pk PRIMARY KEY,
    username VARCHAR(128)
        CONSTRAINT users_username_uniq UNIQUE
        CONSTRAINT users_username_not_null NOT NULL
);

--liquibase formatted sql

--changeset ilyin:create-users-schema
CREATE SCHEMA users;

--changeset ilyin:create-auth-schema
CREATE SCHEMA auth;

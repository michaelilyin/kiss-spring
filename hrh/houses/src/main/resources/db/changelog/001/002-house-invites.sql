--liquibase formatted sql

--changeset ilyin:added-invite-tables context:prod
CREATE TYPE house_invite_resolution
AS ENUM (
    'accepted',
    'rejected',
    'cancelled'
    );

CREATE TABLE IF NOT EXISTS house_invites
(
    id                BIGSERIAL PRIMARY KEY,
    house_id          BIGINT                   NOT NULL REFERENCES houses (id),
    user_email        VARCHAR(128)             NOT NULL,
    invited_by        UUID                     NOT NULL,
    invited_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    invitation        VARCHAR(500),
    resolution_status house_invite_resolution,
    resolution        VARCHAR(500),
    resolved_by       UUID,
    resolved_at       TIMESTAMP WITH TIME ZONE
);

--liquibase formatted sql

--changeset ilyin:added-invite-tables context:prod
CREATE TABLE IF NOT EXISTS house_invite_resolutions
(
    key VARCHAR(64) PRIMARY KEY
);

INSERT INTO house_invite_resolutions (key)
VALUES ('ACCEPTED'),
       ('REJECTED'),
       ('CANCELLED');

CREATE TABLE IF NOT EXISTS house_invites
(
    id                BIGSERIAL PRIMARY KEY,
    house_id          BIGINT                   NOT NULL REFERENCES houses (id),
    user_email        VARCHAR(128)             NOT NULL,
    invited_by        UUID                     NOT NULL,
    invited_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    invitation        VARCHAR(500),
    resolution_status VARCHAR(64) NOT NULL REFERENCES house_invite_resolutions (key),
    resolution        VARCHAR(500),
    resolved_by       UUID,
    resolved_at       TIMESTAMP WITH TIME ZONE
);

--changeset ilyin:add-new-invite-type context:prod
INSERT INTO house_invite_resolutions (key)
VALUES ('NEW');

--changeset ilyin:not-null-invitation context:prod
ALTER TABLE house_invites
    ALTER COLUMN invitation SET NOT NULL;

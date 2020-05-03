--liquibase formatted sql

--changeset ilyin:create-houses-table context:prod
CREATE TABLE houses
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    created_by UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

--changeset ilyin:create-house-users-link context:prod
CREATE TABLE IF NOT EXISTS house_users (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    house_id BIGINT NOT NULL REFERENCES houses(id),
    attached_by UUID NOT NULL,
    attached_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

    CONSTRAINT unique_user_per_house UNIQUE (user_id, house_id)
);

--changeset ilyin:add-description-column context:prod
ALTER TABLE houses
    ADD COLUMN description VARCHAR(300);

--changeset ilyin:add-owner-column context:prod
ALTER TABLE houses
    ADD COLUMN IF NOT EXISTS owned_by UUID;

UPDATE houses
SET owned_by = created_by
WHERE owned_by IS NULL;

ALTER TABLE houses
    ALTER COLUMN owned_by SET NOT NULL;

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

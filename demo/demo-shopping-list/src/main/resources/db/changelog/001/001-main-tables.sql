--liquibase formatted sql

--changeset ilyin:create-good-table context:prod
CREATE TABLE unit_of_measure
(
    id VARCHAR(16) PRIMARY KEY
);

CREATE TABLE goods
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    description TEXT
);

INSERT INTO goods (name, description)
VALUES ('Bread', 'Just bread'),
       ('Milk', 'Moo moo'),
       ('Chicken eggs', 'Best for breakfast'),
       ('Chicken breast', 'Classic...'),
       ('Potatoes', 'Real food!'),
       ('Carrot', 'Are you rabbit?!'),
       ('Rum', 'Yo-ho-ho'),
       ('Corned beef', 'Very dry'),
       ('Pork', 'For roasting on the coals'),
       ('Tomato', 'Red, tasty'),
       ('Cucumber', 'Just cucumber'),
       ('Beer', 'Brew. Enjoy. Empty. Repeat.');

--changeset ilyin:default-quantities context:prod
INSERT INTO unit_of_measure(id)
VALUES ('PCS'),
       ('PACKAGE'),
       ('LITER'),
       ('BOTTLE'),
       ('KILOGRAM')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS shopping_lists
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128)             NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    created_by  UUID                     NOT NULL,
    description TEXT
);

INSERT INTO shopping_lists
    (name, created_at, created_by, description)
VALUES ('The pirate list', now(), '9eee848c-3b5a-4255-b367-09e0572099ab',
        'Demo shopping list allowed for all. Just for show functionality of the App.');

INSERT INTO shopping_lists
    (name, created_at, created_by, description)
VALUES ('BBQ list', now(), '9eee848c-3b5a-4255-b367-09e0572099ab',
        'Demo shopping list allowed for all. Just for show functionality of the App.');

CREATE TABLE IF NOT EXISTS shopping_list_item_state
(
    id VARCHAR(32) PRIMARY KEY
);

INSERT INTO shopping_list_item_state (id)
VALUES ('ACTIVE'),
       ('COMPLETED'),
       ('EXCLUDED');

CREATE TABLE IF NOT EXISTS shopping_list_item
(
    id       BIGSERIAL PRIMARY KEY,
    good_id  BIGINT      NOT NULL REFERENCES goods (id),
    list_id  BIGINT      NOT NULL REFERENCES shopping_lists (id),
    quantity INT         NOT NULL,
    measure  VARCHAR(16) NOT NULL REFERENCES unit_of_measure (id),
    state    VARCHAR(32) NOT NULL REFERENCES shopping_list_item_state (id) DEFAULT 'ACTIVE',

    UNIQUE (good_id, list_id)
);

INSERT INTO shopping_list_item (good_id, list_id, quantity, measure)
VALUES ((SELECT id FROM goods WHERE name = 'Rum'),
        (SELECT id FROM shopping_lists WHERE name = 'The pirate list'),
        99,
        'BOTTLE'),
       ((SELECT id FROM goods WHERE name = 'Corned beef'),
        (SELECT id FROM shopping_lists WHERE name = 'The pirate list'),
        300,
        'KILOGRAM');

INSERT INTO shopping_list_item (good_id, list_id, quantity, measure)
VALUES ((SELECT id FROM goods WHERE name = 'Pork'),
        (SELECT id FROM shopping_lists WHERE name = 'BBQ list'),
        5,
        'KILOGRAM'),
       ((SELECT id FROM goods WHERE name = 'Tomato'),
        (SELECT id FROM shopping_lists WHERE name = 'BBQ list'),
        10,
        'PCS'),
       ((SELECT id FROM goods WHERE name = 'Potatoes'),
        (SELECT id FROM shopping_lists WHERE name = 'BBQ list'),
        3,
        'KILOGRAM'),
       ((SELECT id FROM goods WHERE name = 'Cucumber'),
        (SELECT id FROM shopping_lists WHERE name = 'BBQ list'),
        20,
        'PCS'),
       ((SELECT id FROM goods WHERE name = 'Beer'),
        (SELECT id FROM shopping_lists WHERE name = 'BBQ list'),
        20,
        'LITER');

--changeset ilyin:archive_shopping_lists context:prod
ALTER TABLE shopping_lists
    ADD COLUMN archived BOOLEAN NOT NULL DEFAULT FALSE;

--changeset ilyin:files
CREATE TABLE files
(
    id       UUID PRIMARY KEY,
    filename VARCHAR NOT NULL,
    data     BYTEA
);

ALTER TABLE goods
    ADD COLUMN image UUID REFERENCES files (id);

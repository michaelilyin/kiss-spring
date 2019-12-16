--liquibase formatted sql

--changeset ilyin:create-client-auth-tables context:prod
CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               VARCHAR(255) PRIMARY KEY,
    resource_ids            VARCHAR(255),
    client_secret           VARCHAR(255),
    scope                   VARCHAR(255),
    authorized_grant_types  VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities             VARCHAR(255),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(255)
);

--changeset ilyin:insert-main-client context:prod
INSERT INTO oauth_client_details (client_id,
                                  resource_ids,
                                  client_secret,
                                  scope,
                                  authorized_grant_types,
                                  web_server_redirect_uri,
                                  authorities,
                                  access_token_validity,
                                  refresh_token_validity,
                                  additional_information,
                                  autoapprove)
VALUES ('main-client',
        '',
        '$2a$10$mL6qX5QfDbNSLkiYqCd2BOiA9wHD/16Cx06dHpspsjM6d85fPMv4C',
        'read,write',
        'password,refresh_token',
        '',
        '',
        300,
        86400,
        '{}',
        '')
ON CONFLICT DO NOTHING;

--changeset ilyin:create-auth-tables context:prod
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL,
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE,
    system   BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS permissions
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(128) NOT NULL,
    name        VARCHAR(512) NOT NULL,
    description TEXT         NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS permissions_code_idx ON permissions (code);

CREATE TABLE IF NOT EXISTS roles
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(256) NOT NULL,
    name        VARCHAR(512) NOT NULL,
    description TEXT         NOT NULL,
    system      BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX IF NOT EXISTS roles_code_idx ON roles (code);

CREATE TABLE IF NOT EXISTS role_permissions
(
    role_id       BIGINT                   NOT NULL,
    permission_id BIGINT                   NOT NULL,
    system        BOOLEAN                  NOT NULL DEFAULT FALSE,
    grant_time    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    grant_user_id BIGINT                   NOT NULL REFERENCES users (id),

    CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id       BIGINT                   NOT NULL,
    role_id       BIGINT                   NOT NULL,
    system        BOOLEAN                  NOT NULL DEFAULT FALSE,
    grant_time    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    grant_user_id BIGINT                   NOT NULL REFERENCES users (id),

    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id)
);

--changeset ilyin:insert-system-default-auth-data context:prod
INSERT INTO users (username, password, enabled, system)
VALUES ('admin', '$2a$10$Ae3Lv/HiiKqEV3G8/U7MCuUwlCSNvqtSFmwykt5c/tE2p0qy7WV3O', TRUE, TRUE),
       ('user', '$2a$10$Ae3Lv/HiiKqEV3G8/U7MCuUwlCSNvqtSFmwykt5c/tE2p0qy7WV3O', TRUE, TRUE)
ON CONFLICT DO NOTHING;

INSERT INTO permissions (code, name, description)
VALUES ('self_r', 'View self account data',
        'Allow user to view account specific data only about self'),
       ('users_r', 'View users data', 'Allow user to view all users account data'),
       ('users_w', 'Manage users', 'Allow user to manage another user accounts')
ON CONFLICT DO NOTHING;

INSERT INTO roles (code, name, description, system)
VALUES ('user', 'User', 'Regular user', TRUE),
       ('user-admin', 'Users administrator', 'Role with grants for manage another users', TRUE)
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id, system, grant_user_id)
SELECT u.id, r.id, TRUE, g.id
FROM users u,
     roles r,
     users g
WHERE u.username == 'admin'
  AND r.code IN ('user', 'user-admin')
  AND g.username == 'admin'
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id, system, grant_user_id)
SELECT u.id, r.id, TRUE, g.id
FROM users u,
     roles r,
     users g
WHERE u.username == 'user'
  AND r.code IN ('user')
  AND g.username == 'admin'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id, system, grant_user_id)
SELECT r.id, p.id, TRUE, g.id
FROM roles r,
     permissions p,
     users g
WHERE r.code = 'user'
  AND p.code IN ('self_r')
  AND g.username == 'admin'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id, system, grant_user_id)
SELECT r.id, p.id, TRUE, g.id
FROM roles r,
     permissions p,
     users g
WHERE r.code = 'admin'
  AND p.code IN ('users_r', 'users_w')
  AND g.username == 'admin'
ON CONFLICT DO NOTHING;

--changeset ilyin:add-name-column-to-users context:prod
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS first_name VARCHAR(128);
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS last_name VARCHAR(128);

--changeset ilyin:update-names context:prod
UPDATE users
SET first_name = upper(username)
WHERE first_name IS NULL;

--changeset ilyin:make-first-name-required:prod
ALTER TABLE users
    ALTER COLUMN first_name SET NOT NULL;

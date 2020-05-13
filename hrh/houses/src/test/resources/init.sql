TRUNCATE TABLE house_invites CASCADE;
TRUNCATE TABLE house_users CASCADE;
TRUNCATE TABLE houses CASCADE;

INSERT INTO houses (id, name, created_by, owned_by, created_at)
VALUES
-- common test data
(1, 'Common Test House', '71a0f36b-69fb-42ec-9fa8-181a3d80bb9e', '71a0f36b-69fb-42ec-9fa8-181a3d80bb9e', '2020-05-11T13:32:48.409Z')
;

ALTER SEQUENCE houses_id_seq RESTART WITH 1000;

INSERT INTO house_invites (id, house_id, user_email, invited_by, invited_at, invitation, resolution_status)
VALUES
-- common test data
(1, 1, 'john@hrh.ru', '71a0f36b-69fb-42ec-9fa8-181a3d80bb9e', '2020-05-11T13:42:48.409Z', 'Please, join', 'NEW'),
(2, 1, 'jim@hrh.ru', '71a0f36b-69fb-42ec-9fa8-181a3d80bb9e', '2020-05-11T13:54:23.231Z', 'Please, join', 'NEW')
;

ALTER SEQUENCE house_invites_id_seq RESTART WITH 1000;

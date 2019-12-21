DELETE FROM users WHERE system IS FALSE;
DELETE FROM roles WHERE system IS FALSE;
DELETE FROM role_permissions WHERE system IS FALSE;
DELETE FROM user_roles WHERE system IS FALSE;
DELETE FROM default_roles WHERE system IS FALSE;



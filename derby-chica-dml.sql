INSERT INTO CREDENTIALS (USER_NAME, PASSWORD)
VALUES
('hugo', '$2a$10$iNZser2g65z6gMT2C7OiZ.nQYpw2dyu.fAUasO..Ej8tpbUqddSq.'),
('paco', '$2a$10$iNZser2g65z6gMT2C7OiZ.nQYpw2dyu.fAUasO..Ej8tpbUqddSq.'),
('luis', '$2a$10$iNZser2g65z6gMT2C7OiZ.nQYpw2dyu.fAUasO..Ej8tpbUqddSq.'),
('pato', '$2a$10$iNZser2g65z6gMT2C7OiZ.nQYpw2dyu.fAUasO..Ej8tpbUqddSq.');

INSERT INTO ROLES (ROLE_ID,DESCRIPTION)
VALUES
('ROLE_USER', 'Regular system user'),
('ROLE_ADMIN', 'Admin user'),
('ROLE_DBA', 'DBA user');


INSERT INTO USER_ACCOUNT (USER_UUID, USER_NAME, DISPLAY_NAME)
VALUES
('uuid1', 'hugo', 'Hugo Boss'),
('uuid2', 'paco', 'Paco-merte mejor'),
('uuid3', 'luis', 'Su nombre artistico es'),
('uuid4', 'pato', 'Pato mio');



INSERT INTO USER_ROLES (USER_UUID, ROLE_ID)
VALUES
('uuid1', 'ROLE_USER'),
('uuid2', 'ROLE_ADMIN'),
('uuid3', 'ROLE_DBA'),
('uuid3', 'ROLE_ADMIN'),
('uuid4', 'ROLE_DBA');

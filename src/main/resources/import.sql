-- noinspection SqlNoDataSourceInspectionForFile
INSERT INTO USER_PROFILE (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ACTIVATED) VALUES (1, 'skymarlio82', '$2a$10$IExICE3TXCR2c3W/QZM1ruKxDmoW9t0grhaku3R3DJaMLqRSmsIbK', 'Jitao', 'LIU', 'jitao.liu82@gmail.com', 1);
INSERT INTO USER_PROFILE (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ACTIVATED) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', 1);
INSERT INTO USER_PROFILE (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ACTIVATED) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0);

INSERT INTO AUTHORITY (ID, NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (ID, NAME) VALUES (2, 'ROLE_USER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (3, 'ROLE_ANONYMOUS');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (2, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 2);

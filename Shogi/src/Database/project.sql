use student_space;
DROP TABLE contact;
DROP TABLE message;
DROP TABLE user;

CREATE TABLE user
(
    username varchar(32),
    password varbinary(16)
);

CREATE TABLE contact
(
    username varchar(32),
    contact varchar(32)
);

CREATE TABLE message
(
    sender varchar(32),
    receiver varchar(32),
    message varchar(256),
    date date
);

ALTER TABLE user
ADD CONSTRAINT user_username_pk PRIMARY KEY(username);

ALTER TABLE contact
ADD CONSTRAINT contact_username_contact_pk PRIMARY KEY(username,contact);

ALTER TABLE message
ADD CONSTRAINT message_sender_receiver_pk PRIMARY KEY(sender,receiver);

ALTER TABLE contact
ADD CONSTRAINT contact_username_fk FOREIGN KEY(username)
REFERENCES user(username);

ALTER TABLE contact
ADD CONSTRAINT contact_contact_fk FOREIGN KEY(contact)
REFERENCES user(username);

ALTER TABLE message
ADD CONSTRAINT message_sender_fk FOREIGN KEY(sender)
REFERENCES user(username);

ALTER TABLE message
ADD CONSTRAINT message_receiver_fk FOREIGN KEY(receiver)
REFERENCES user(username);

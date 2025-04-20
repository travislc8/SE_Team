use student_space;
DROP TABLE user;

CREATE TABLE user
(
    username varchar(32),
    password varbinary(16)
);

ALTER TABLE user
ADD CONSTRAINT user_username_pk PRIMARY KEY(username);

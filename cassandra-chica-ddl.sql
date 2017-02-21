CREATE TABLE user_roles (
    role text,
    username text,
    password text,
    email text,
    age int,
    PRIMARY KEY (username, role)
);

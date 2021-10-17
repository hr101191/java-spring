--liquibase formatted sql
--adapted for h2
--source: https://github.com/spring-projects/spring-security/blob/5.5.1/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
--changeset: authorities:create
CREATE TABLE IF NOT EXISTS authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username on authorities (username,authority);
--liquibase formatted sql
--adapted for h2
--source: https://github.com/spring-projects/spring-security/blob/5.5.1/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
--changeset: users:create
CREATE TABLE IF NOT EXISTS users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);
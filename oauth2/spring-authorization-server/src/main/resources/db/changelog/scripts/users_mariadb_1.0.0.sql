--liquibase formatted sql
--adapted for mariadb
--source: https://github.com/spring-projects/spring-security/blob/5.5.1/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
--changeset: users:create
CREATE TABLE IF NOT EXISTS `users` (
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` text(500) NOT NULL,
  `ENABLED` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`USERNAME`)
) ENGINE=InnoDB;
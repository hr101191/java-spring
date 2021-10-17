--liquibase formatted sql
--adapted for mariadb
--source: https://github.com/spring-projects/spring-security/blob/5.5.1/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
--changeset: authorities:create
CREATE TABLE IF NOT EXISTS `authorities` (
  `USERNAME` varchar(50) NOT NULL,
  `AUTHORITY` varchar(50) NOT NULL,
  UNIQUE KEY `AUTHORITIES_UNIQUE` (`USERNAME`,`AUTHORITY`),
  CONSTRAINT `AUTHORITIES_FK1` FOREIGN KEY (`USERNAME`) REFERENCES `users` (`USERNAME`)
) ENGINE=InnoDB;
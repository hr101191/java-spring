--liquibase formatted sql
--adapted for mariadb
--source: https://github.com/spring-projects/spring-authorization-server/blob/0.2.0/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql
--changeset: oauth2_authorization_consent:create
CREATE TABLE IF NOT EXISTS `oauth2_authorization_consent` (
  `registered_client_id` varchar(100) NOT NULL,
  `principal_name` varchar(200) NOT NULL,
  `authorities` varchar(1000) NOT NULL,
  PRIMARY KEY (`registered_client_id`,`principal_name`)
) ENGINE=InnoDB;
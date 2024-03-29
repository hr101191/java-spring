--liquibase formatted sql
--adapted for mariadb
--source: https://github.com/spring-projects/spring-authorization-server/blob/0.2.0/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
--changeset: oauth2_registered_client:create
CREATE TABLE IF NOT EXISTS `oauth2_registered_client` (
  `id` varchar(100) NOT NULL,
  `client_id` varchar(100) NOT NULL,
  `client_id_issued_at` datetime NOT NULL DEFAULT current_timestamp(),
  `client_secret` varchar(200) DEFAULT NULL,
  `client_secret_expires_at` datetime DEFAULT NULL,
  `client_name` varchar(200) NOT NULL,
  `client_authentication_methods` varchar(1000) NOT NULL,
  `authorization_grant_types` varchar(1000) NOT NULL,
  `redirect_uris` varchar(1000) DEFAULT NULL,
  `scopes` varchar(1000) NOT NULL,
  `client_settings` varchar(2000) NOT NULL,
  `token_settings` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
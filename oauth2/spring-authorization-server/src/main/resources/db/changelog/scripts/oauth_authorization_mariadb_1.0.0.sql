--liquibase formatted sql
--adapted for mariadb
--source: https://github.com/spring-projects/spring-authorization-server/blob/0.2.0/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
--changeset: oauth2_authorization:create
CREATE TABLE IF NOT EXISTS `oauth2_authorization` (
  `id` varchar(100) NOT NULL,
  `registered_client_id` varchar(100) NOT NULL,
  `principal_name` varchar(200) NOT NULL,
  `authorization_grant_type` varchar(100) NOT NULL,
  `attributes` varchar(4000) DEFAULT NULL,
  `state` varchar(500) DEFAULT NULL,
  `authorization_code_value` blob DEFAULT NULL,
  `authorization_code_issued_at` datetime DEFAULT NULL,
  `authorization_code_expires_at` datetime DEFAULT NULL,
  `authorization_code_metadata` varchar(2000) DEFAULT NULL,
  `access_token_value` blob DEFAULT NULL,
  `access_token_issued_at` datetime DEFAULT NULL,
  `access_token_expires_at` datetime DEFAULT NULL,
  `access_token_metadata` varchar(2000) DEFAULT NULL,
  `access_token_type` varchar(100) DEFAULT NULL,
  `access_token_scopes` varchar(1000) DEFAULT NULL,
  `oidc_id_token_value` blob DEFAULT NULL,
  `oidc_id_token_issued_at` datetime DEFAULT NULL,
  `oidc_id_token_expires_at` datetime DEFAULT NULL,
  `oidc_id_token_metadata` varchar(2000) DEFAULT NULL,
  `refresh_token_value` blob DEFAULT NULL,
  `refresh_token_issued_at` datetime DEFAULT NULL,
  `refresh_token_expires_at` datetime DEFAULT NULL,
  `refresh_token_metadata` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
--liquibase formatted sql
--adapted for h2
--source: https://github.com/spring-projects/spring-session/blob/2.5.1/spring-session-jdbc/src/main/resources/org/springframework/session/jdbc/schema-h2.sql
--changeset: spring_session:create
CREATE TABLE IF NOT EXISTS SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);
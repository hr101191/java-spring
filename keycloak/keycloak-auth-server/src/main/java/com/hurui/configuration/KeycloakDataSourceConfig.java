package com.hurui.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.keycloak.server.datasource")
public class KeycloakDataSourceConfig {

	private String url;
	private String driver;
	private String driverDialect;
	private String user;
	private String password;
	private String initializeEmpty;
	private String migrationStrategy;
	private String showSql;
	private String formatSql;
	private String globalStatsInterval;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDriverDialect() {
		return driverDialect;
	}

	public void setDriverDialect(String driverDialect) {
		this.driverDialect = driverDialect;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInitializeEmpty() {
		return initializeEmpty;
	}

	public void setInitializeEmpty(String initializeEmpty) {
		this.initializeEmpty = initializeEmpty;
	}

	public String getMigrationStrategy() {
		return migrationStrategy;
	}

	public void setMigrationStrategy(String migrationStrategy) {
		this.migrationStrategy = migrationStrategy;
	}

	public String getShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}

	public String getFormatSql() {
		return formatSql;
	}

	public void setFormatSql(String formatSql) {
		this.formatSql = formatSql;
	}

	public String getGlobalStatsInterval() {
		return globalStatsInterval;
	}

	public void setGlobalStatsInterval(String globalStatsInterval) {
		this.globalStatsInterval = globalStatsInterval;
	}

}

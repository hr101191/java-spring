package com.hurui.spring.repository;

import java.util.Map;

import javax.sql.DataSource;

import reactor.core.publisher.Flux;

public interface JdbcRepositoryAsyncWrapper {
	public Flux<Map<String, Object>> queryForListAsync(DataSource dataSource, String query);
}

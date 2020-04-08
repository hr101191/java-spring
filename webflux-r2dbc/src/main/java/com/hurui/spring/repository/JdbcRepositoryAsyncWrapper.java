package com.hurui.spring.repository;

import java.util.Map;

import javax.sql.DataSource;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JdbcRepositoryAsyncWrapper {
	public Flux<Map<String, Object>> queryForListAsync(DataSource dataSource, String query);
	public Mono<Map<String, Object>> mockSimpleJdbcCallAsync(DataSource dataSource, String query);
}

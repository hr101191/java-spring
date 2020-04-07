package com.hurui.spring.repositoryimpl;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurui.spring.repository.JdbcRepositoryAsyncWrapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
public class JdbcRepositoryAsyncWrapperImpl implements JdbcRepositoryAsyncWrapper {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	@Override
	public Flux<Map<String, Object>> queryForListAsync(DataSource dataSource, String query){
		return Mono.fromCallable(() -> {
			return queryForList(dataSource, query);
		})
				.flatMapMany(mapper -> Flux.fromIterable(mapper))
				.doOnError(error -> logger.error("Error executing query: [{}], Error Message: [{}], stacktrace: ", query, error.getMessage(), error))
				.subscribeOn(Schedulers.boundedElastic());
	}
		
	private List<Map<String, Object>> queryForList(DataSource dataSource, String query){
		logger.info("Executing SQL Query: [{}]", query);
		return new JdbcTemplate(dataSource).queryForList(query);
	}
	
	//TODO: Async wrapper which maps to POJO
	public <T> Flux<Map<String, Object>> queryForListAsyncMapTo(DataSource dataSource, String query, Class<T> clazz){
		return Mono.fromCallable(() -> {
			return queryForList(dataSource, query);
		})
				.doOnError(error -> logger.error("Error executing query: [{}], Error Message: [{}], stacktrace: ", query, error.getMessage(), error))
				.flatMapMany(mapper -> Flux.fromIterable(mapper))
				.subscribeOn(Schedulers.boundedElastic());
	}
	
	//TODO: Jackson Object Mapper to map jdbc result to POJO
	@SuppressWarnings("unused")
	private static <T> void mapTo(List<Map<String, Object>> jdbcResultMaps, Class<T> clazz) {
		objectMapper.convertValue(jdbcResultMaps, clazz);
	}
}

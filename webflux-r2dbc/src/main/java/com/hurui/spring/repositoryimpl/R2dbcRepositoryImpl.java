package com.hurui.spring.repositoryimpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.hurui.spring.repository.R2dbcRepository;

import reactor.core.publisher.Flux;

@Repository
public class R2dbcRepositoryImpl implements R2dbcRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	private DatabaseClient databaseClient;
	
	public R2dbcRepositoryImpl(DatabaseClient databaseClient) {
		this.databaseClient = databaseClient; 
	}
	
	@Override
	public Flux<Map<String, Object>>findAll(String query){		
		return databaseClient.execute(query)
				.fetch()
				.all()
				.doOnSubscribe(onSubscribe -> {logger.info("Executing SQL Query: [{}]", query);})
				.doOnError(error -> logger.error("Error executing query: [{}], Error Message: [{}], stacktrace: ", query, error.getMessage(), error));
	}
}

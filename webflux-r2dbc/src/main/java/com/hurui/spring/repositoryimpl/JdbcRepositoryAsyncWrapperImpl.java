package com.hurui.spring.repositoryimpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
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
	
	@Override
	public Mono<Map<String, Object>> mockSimpleJdbcCallAsync(DataSource dataSource, String query){
		return Mono.fromCallable(() -> {
			return mockJdbcData();
		})
				.doOnSubscribe(onSubscribe -> logger.info("Into test method......"))
				.doOnError(error -> logger.error("Error executing stored procedure: [{}], Error Message: [{}], stacktrace: ", query, error.getMessage(), error))
				.subscribeOn(Schedulers.boundedElastic());			
	}
	
	//Not working yet, TODO: find a way to wrap the returned data
	public Flux<Map<String, Object>> simpleJdbcCallAsync(DataSource dataSource, String query){
		return Mono.fromCallable(() -> {
			return simpleJdbcCall(dataSource, query);
		})
				.expand(expander -> Flux.just(expander))
				.doOnError(error -> logger.error("Error executing stored procedure: [{}], Error Message: [{}], stacktrace: ", query, error.getMessage(), error))
				.subscribeOn(Schedulers.boundedElastic());			
	}
		
	private List<Map<String, Object>> queryForList(DataSource dataSource, String query){
		logger.info("Executing SQL Query: [{}]", query);
		return new JdbcTemplate(dataSource).queryForList(query);
	}
	
	private Map<String, Object> simpleJdbcCall(DataSource dataSource, String procedureName){
		return new SimpleJdbcCall(new JdbcTemplate(dataSource)).withProcedureName(procedureName).execute();
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
	
	public Map<String, Integer> test(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 123);
		map.put("b", 222);
		return map;
	}
	
	public Map<String, Object> mockJdbcData(){
		Map<String, Object> mockdata = new HashMap<>();
		
		//#result-set-1
		Set<Object> resultSet1 = new HashSet<>();
		Map<String, Object> rs1row1 = new HashMap<>();
		rs1row1.put("id", "1");
		rs1row1.put("title", "post one");
		rs1row1.put("content", "content of post one");
		Map<String, Object> rs1row2= new HashMap<>();
		rs1row2.put("id", "2");
		rs1row2.put("title", "post two");
		rs1row2.put("content", "content of post two");
		Map<String, Object> rs1row3= new HashMap<>();
		rs1row3.put("id", "3");
		rs1row3.put("title", "post three");
		rs1row3.put("content", "content of post three");
		resultSet1.add(rs1row1);
		resultSet1.add(rs1row2);
		resultSet1.add(rs1row3);
		
		//#result-set-2
		Set<Object> resultSet2 = new HashSet<>();
		Map<String, Object> rs2row1 = new HashMap<>();
		rs2row1.put("id", "1");
		rs2row1.put("description", "some description");
		resultSet2.add(rs2row1);
		
		//#result-set-3
		Set<Object> resultSet3 = new HashSet<>();
		Map<String, Object> rs3row1 = new HashMap<>();
		rs3row1.put("id", "1");
		rs3row1.put("name", "John");
		rs3row1.put("organization", "Apple");
		rs3row1.put("position", "account manager");
		Map<String, Object> rs3row2= new HashMap<>();
		rs3row2.put("id", "2");
		rs3row2.put("name", "Leslie");
		rs3row2.put("organization", "Dell");
		rs3row2.put("position", "developer");
		resultSet3.add(rs3row1);
		resultSet3.add(rs3row2);
		
		//#result-set-4
		Set<Object> resultSet4 = new HashSet<>();
		Map<String, Object> rs4row1 = new HashMap<>();
		rs4row1.put("id", "1");
		rs4row1.put("name", "Giannis Antetokounmpo");
		rs4row1.put("organization", "MIL");
		rs4row1.put("position", "PF");
		rs4row1.put("rating", "54.4");
		Map<String, Object> rs4row2= new HashMap<>();
		rs4row2.put("id", "2");
		rs4row2.put("name", "James Harden");
		rs4row2.put("organization", "HOU");
		rs4row2.put("position", "SG");
		rs4row2.put("rating", "54.4");
		Map<String, Object> rs4row3= new HashMap<>();
		rs4row3.put("id", "3");
		rs4row3.put("name", "Luka Doncic");
		rs4row3.put("organization", "DAL");
		rs4row3.put("position", "SG");
		rs4row3.put("rating", "50.5");
		Map<String, Object> rs4row4= new HashMap<>();
		rs4row4.put("id", "4");
		rs4row4.put("name", "Lebron James");
		rs4row4.put("organization", "LAL");
		rs4row4.put("position", "PF");
		rs4row4.put("rating", "50.2");
		Map<String, Object> rs4row5= new HashMap<>();
		rs4row5.put("id", "5");
		rs4row5.put("name", "Karl-Anthony Towns");
		rs4row5.put("organization", "MIN");
		rs4row5.put("position", "C");
		rs4row5.put("rating", "47.7");
		resultSet4.add(rs4row1);
		resultSet4.add(rs4row2);
		resultSet4.add(rs4row3);
		resultSet4.add(rs4row4);
		resultSet4.add(rs4row5);
		
		mockdata.put("#result-set-1", resultSet1);
		mockdata.put("#result-set-2", resultSet2);
		mockdata.put("#result-set-3", resultSet3);
		mockdata.put("#result-set-4", resultSet4);
		return mockdata;
	}
}

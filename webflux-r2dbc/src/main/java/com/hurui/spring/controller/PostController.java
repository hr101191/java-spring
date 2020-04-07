package com.hurui.spring.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hurui.spring.model.CommonHttpRespBodyModel;
import com.hurui.spring.repository.JdbcRepositoryAsyncWrapper;
import com.hurui.spring.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

@RestController
public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	private R2dbcRepository repository;
	private JdbcRepositoryAsyncWrapper jdbcRepository;
	private DataSource dataSource;
	
	public PostController(R2dbcRepository repository, JdbcRepositoryAsyncWrapper jdbcRepository, DataSource dataSource) {
		this.repository = repository;
		this.jdbcRepository = jdbcRepository;
		this.dataSource = dataSource;
	}
	
	@GetMapping(value = "/api/reactive/post/getall")
	public Mono<ResponseEntity<CommonHttpRespBodyModel>> getAllPost(HttpServletRequest request){
		UUID requestId = UUID.randomUUID();
		logger.info("Handling request from: [{}] | Assigning RequestID: [{}]", request.getRequestURI(), requestId);
		return repository.findAll("select * from posts")
				.collectList()
				.map(mapper -> new ResponseEntity<>(
							new CommonHttpRespBodyModel(requestId,
									HttpStatus.OK.value(), 
									mapper),
							HttpStatus.OK
						))
				.doOnError(onError -> logger.warn("Completed request with error! Request ID: [{}]", requestId))
				.doOnSuccess(onSuccess -> logger.info("Completed request successfully... Request ID: [{}]", requestId))
				.onErrorResume(fallback -> Mono.just(new ResponseEntity<>(
						new CommonHttpRespBodyModel(requestId,
								HttpStatus.INTERNAL_SERVER_ERROR.value(), 
								"INTERNAL SERVER ERROR, error message: [" + fallback.getMessage() + "]"),
						HttpStatus.INTERNAL_SERVER_ERROR)));
	}
	
	@GetMapping(value = "/api/reactive/post/getall/error")
	public Mono<ResponseEntity<CommonHttpRespBodyModel>> getAllPostErrorDemo(HttpServletRequest request){
		UUID requestId = UUID.randomUUID();
		logger.info("Handling request from: [{}] | Assigning RequestID: [{}]", request.getRequestURI(), requestId);
		return repository.findAll("select * from post")
				.collectList()
				.map(mapper -> new ResponseEntity<>(
							new CommonHttpRespBodyModel(requestId,
									HttpStatus.OK.value(), 
									mapper),
							HttpStatus.OK
						))
				.doOnError(onError -> logger.warn("Completed request with error! Request ID: [{}]", requestId))
				.doOnSuccess(onSuccess -> logger.info("Completed request successfully... Request ID: [{}]", requestId))
				.onErrorResume(fallback -> Mono.just(new ResponseEntity<>(
						new CommonHttpRespBodyModel(requestId,
								HttpStatus.INTERNAL_SERVER_ERROR.value(), 
								"INTERNAL SERVER ERROR, error message: [" + fallback.getMessage() + "]"),
						HttpStatus.INTERNAL_SERVER_ERROR)));				
				
	}
	
	@GetMapping(value = "/api/callable/post/getall")
	public Mono<ResponseEntity<CommonHttpRespBodyModel>> getAllPostFromCallable(HttpServletRequest request){
		UUID requestId = UUID.randomUUID();
		logger.info("Handling request from: [{}] | Assigning RequestID: [{}]", request.getRequestURI(), requestId);
		return jdbcRepository.queryForListAsync(dataSource, "select * from posts")
				.collectList()
				.map(mapper -> new ResponseEntity<>(
							new CommonHttpRespBodyModel(requestId,
									HttpStatus.OK.value(), 
									mapper),
							HttpStatus.OK
						))
				.doOnError(onError -> logger.warn("Completed request with error! Request ID: [{}]", requestId))
				.doOnSuccess(onSuccess -> logger.info("Completed request successfully... Request ID: [{}]", requestId))
				.onErrorResume(fallback -> Mono.just(new ResponseEntity<>(
						new CommonHttpRespBodyModel(requestId,
								HttpStatus.INTERNAL_SERVER_ERROR.value(), 
								"INTERNAL SERVER ERROR, error message: [" + fallback.getMessage() + "]"),
						HttpStatus.INTERNAL_SERVER_ERROR)));
	}
	
	@GetMapping(value = "/api/callable/post/getall/error")
	public Mono<ResponseEntity<CommonHttpRespBodyModel>> getAllPostFromCallableErrorDemo(HttpServletRequest request){
		UUID requestId = UUID.randomUUID();
		logger.info("Handling request from: [{}] | Assigning RequestID: [{}]", request.getRequestURI(), requestId);
		return jdbcRepository.queryForListAsync(dataSource, "select * from post")
				.collectList()
				.map(mapper -> new ResponseEntity<>(
							new CommonHttpRespBodyModel(requestId,
									HttpStatus.OK.value(), 
									mapper),
							HttpStatus.OK
						))
				.onErrorResume(fallback -> Mono.just(new ResponseEntity<>(
						new CommonHttpRespBodyModel(requestId,
								HttpStatus.INTERNAL_SERVER_ERROR.value(), 
								"INTERNAL SERVER ERROR, error message: [" + fallback.getMessage() + "]"),
						HttpStatus.INTERNAL_SERVER_ERROR)))
				.doOnError(onError -> logger.warn("Completed request with error! Request ID: [{}]", requestId))
				.doOnSuccess(onSuccess -> logger.info("Completed request successfully... Request ID: [{}]", requestId));
	}
}

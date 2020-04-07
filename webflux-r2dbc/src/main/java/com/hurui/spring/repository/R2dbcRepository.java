package com.hurui.spring.repository;

import java.util.Map;

import reactor.core.publisher.Flux;

public interface R2dbcRepository {
	public Flux<Map<String, Object>>findAll(String query);
}

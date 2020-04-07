package com.hurui.spring.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class CommonHttpRespBodyModel {

	private UUID requestId = UUID.randomUUID();
	private Integer statusCode;
	private Object message;
	private Timestamp timestamp = new Timestamp(new Date().getTime());
	
	public CommonHttpRespBodyModel() {
		super();
	}
	
	public CommonHttpRespBodyModel(Integer statusCode, Object message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public CommonHttpRespBodyModel(UUID requestId, Integer statusCode, Object message) {
		super();
		this.requestId = requestId;
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}	
}

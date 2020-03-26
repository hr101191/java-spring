package com.hurui.spring.service;

public interface AuthenticationService {
	public boolean validateUserFromActiveDirectory(String username, String password);
	public boolean validateUserFromDatabase(String username, String password);
}

package com.hurui.spring.serviceimpl;

import org.springframework.stereotype.Service;

import com.hurui.spring.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	/**
	 * TODO: add actual implementation, dummy method as of now
	 * Performs authentication from Active directory
	 * 
	 * @param username
	 * @param password
	 *
	 * @return boolean based on whether user is successfully authenticated from Active Directory
	 */
	public boolean validateUserFromActiveDirectory(String username, String password) {
		return true;
	}
	
	/**
	 * TODO: add actual implementation, dummy method as of now
	 * Performs authentication from database
	 * 
	 * @param username
	 * @param password
	 *
	 * @return boolean based on whether user is successfully authenticated from database
	 */
	public boolean validateUserFromDatabase(String username, String password) {
		return true;
	}
}

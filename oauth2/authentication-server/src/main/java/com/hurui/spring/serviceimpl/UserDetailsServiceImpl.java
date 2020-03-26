package com.hurui.spring.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	private static final String DUMMY_PASSWORD = "";

	/**
	 * This method is called whenever grant_type is refresh_token
	 * @param username - this is passed by Spring
	 * 
	 * return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return cloneUser(username);
	}

	/**
	 * TODO: See comments for implementations to be completed
	 * 
	 * This method retrieves the user details from database to form an User object
	 */
	private UserDetails cloneUser(String username) {
		logger.info("Refreshing access_token issued to: [{}]", username);
		
		/*
		 * retrieve user information from database and return user info to complete the refresh token process
		 */		
		if(retrieveUserInfo(username)) {
			return new User(username, DUMMY_PASSWORD, true, true, true, true, AuthorityUtils.createAuthorityList("read", "write"));
		}else {
			throw new UsernameNotFoundException("");
		}
		
	}
	
	/*
	 * Dummy method to simulate successful retrieval of user information from database
	 */
	private boolean retrieveUserInfo(String username) {
		return true;
	}
}

package com.hurui.spring.serviceimpl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hurui.spring.service.AuthenticationService;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	AuthenticationService authenticationService;
	
	private static final String AD_AUTH_PROVIDER_SRTRING = "AD";
	private static final String JDBC_AUTH_PROVIDER_SRTRING = "JDBC";
	
	/**
	 * Overrides the default Authentication Manager used by Spring Oauth2 Library
	 * This method is invoked by Spring Oauth2 Library when /oauth/token endpoint is called
	 * Authentication is done via @Autowired AuthenticationService where custom methods are defined
	 * 
	 * This implementation provides you with the freedom to use your favourite AD and JDBC auth library instead
	 * of being bound by the default Spring's implementation
	 * 
	 * @param authentication
	 *
	 * @return boolean based on whether user is successfully authenticated from Active Directory
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//1) Initialize variables
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		Map<String, Object> authDetailsMap = (Map<String, Object>) authentication.getDetails();
		String authProvider = "";
		
		//2) validate and throw custom error
		if(authDetailsMap.containsKey("auth_provider")) {
			authProvider = (String) authDetailsMap.get("auth_provider");
		}else {
			throw new ProviderNotFoundException("Please identify Auth Provider in payload.");
		}
				
		if(authProvider.isEmpty()){
			throw new ProviderNotFoundException("Auth Provider cannot be null or empty");
		}else if(!(authProvider.equalsIgnoreCase(AD_AUTH_PROVIDER_SRTRING) || authProvider.equalsIgnoreCase(JDBC_AUTH_PROVIDER_SRTRING))) {
			throw new ProviderNotFoundException("Auth Provider is not supported, please choose AD or JDBC only.");
		}
		
		//3) invoke autowired authentication methods if (2) is successful
		if(authProvider.equalsIgnoreCase(AD_AUTH_PROVIDER_SRTRING)) {
			if(authenticationService.validateUserFromActiveDirectory(username, password)) {
				//TODO: retrieve List<GrantAuthority> from database instead of returning an empty arrayList 
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList());
			}else {
				throw new UsernameNotFoundException("Username or password is not found.");
			}	
		}else if(authProvider.equalsIgnoreCase(JDBC_AUTH_PROVIDER_SRTRING)){
			if(authenticationService.validateUserFromDatabase(username, password)) {
				//TODO: retrieve List<GrantAuthority> from database instead of returning an empty arrayList 
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList());
			}else {
				throw new UsernameNotFoundException("Username or password is not found.");
			}
		}else {
			throw new ProviderNotFoundException("Auth Provider is not supported, please choose AD or JDBC only.");
		}
			
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

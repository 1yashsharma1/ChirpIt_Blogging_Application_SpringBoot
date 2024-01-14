package com.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.exceptions.ApiException;
import com.blog.app.payloads.JwtAuthRequest;
import com.blog.app.security.JWTAuthResponse;
import com.blog.app.security.JWTTokenHelper;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JWTTokenHelper tokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

		// first authenticate
		this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

		// load user details
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

		// generate token
		String token = this.tokenHelper.generateToken(userDetails);

		JWTAuthResponse authResponse = new JWTAuthResponse();
		authResponse.setToken(token);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (DisabledException e) {
			e.printStackTrace();
		}
		catch (BadCredentialsException e) {
			throw new ApiException("Invalid Username or password!!");
		}
	}

}

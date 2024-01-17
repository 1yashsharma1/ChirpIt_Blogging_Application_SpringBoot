package com.blog.app.configs;

public class AppConstants {

	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "10";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR = "asc";
	public static final long JWT_TOKEN_VALIDITY = 5 * 1000 * 60L;
	public static final Integer NORMAL_USER = 3;
	public static final Integer ADMIN_USER = 1;
	public static final String PUBLIC_URLS[] = { "/v3/api-docs", "/api/v1/auth/**", "/v2/api-docs",
			"/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };

}

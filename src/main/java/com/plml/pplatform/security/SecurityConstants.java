package com.plml.pplatform.security;

public class SecurityConstants {
	static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864_000_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String AUTHORIZATION_HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/signup";
    static final String REGISTRATION_CONFIRM_URL = "/registrationConfirm";
    static final String USER_RESET_PASSWORD_URL = "/user/resetPassword";
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    static final String RESET_PASSWORD_HEADER_STRING = "PasswordReset";
}

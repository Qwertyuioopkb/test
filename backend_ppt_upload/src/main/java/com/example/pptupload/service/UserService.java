package com.example.pptupload.service;

public interface UserService {
    /**
     * Validates user credentials (registering new users when allowed) and sends a login SMS code.
     *
     * @param username the login username
     * @param password the plaintext password
     * @param phone    the phone number provided by the user (required for registration or binding)
     * @throws IllegalArgumentException if validation fails
     */
    void initiateLogin(String username, String password, String phone);

    /**
     * Verifies the SMS code and issues a JWT token when successful.
     *
     * @param username the login username
     * @param smsCode  the SMS verification code
     * @return a JWT token when verification succeeds; {@code null} otherwise
     */
    String completeLogin(String username, String smsCode);
}

package com.niit.security;

import com.niit.domain.User;

import java.util.Map;

public interface JwtSecurityTokenGenerator {
    /**
     * This method will generate token by taking User details
     *
     * @param user
     * @return Set of Details
     */
    Map<String, String> tokenGenerator(User user);
}

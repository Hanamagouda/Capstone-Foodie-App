package com.niit.security;

import com.niit.domain.User;

import java.util.Map;

public interface JwtSecurityTokenGenerator {
    Map<String, String> tokenGenerator(User user);
}

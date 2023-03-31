package com.niit.security;

import com.niit.domain.Customer;

import java.util.Map;

public interface JwtSecurityTokenGenerator {
    /**
     * This method will generate token by taking User details
     *
     * @param customer
     * @return Set of Details
     */
    Map<String, String> tokenGenerator(Customer customer);
}

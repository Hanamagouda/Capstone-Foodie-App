/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.security;

import com.niit.domain.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSecurityTokenGeneratorImpl implements JwtSecurityTokenGenerator {
    @Override
    public Map<String, String> tokenGenerator(Customer customer) {
        Map<String, String> tokenMap = new HashMap<>();
        customer.setPassword("");
        Map<String, Object> userData = new HashMap<>();
        userData.put("Id", customer.getCustomerEmailId());
        String jwtTokenString = Jwts.builder().setClaims(userData).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, "mySecret").compact();
        tokenMap.put("token", jwtTokenString);
        tokenMap.put("message", "Login Successful");
        tokenMap.put("emailId", customer.getCustomerEmailId());
        tokenMap.put("typeOfUser", customer.getTypeOfUser());
        return tokenMap;
    }
}

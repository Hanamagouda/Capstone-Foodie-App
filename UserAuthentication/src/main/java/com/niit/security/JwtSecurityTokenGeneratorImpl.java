/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.security;

import com.niit.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSecurityTokenGeneratorImpl implements JwtSecurityTokenGenerator {
    @Override
    public Map<String, String> tokenGenerator(User user) {
        Map<String, String> tokenMap = new HashMap<>();
        user.setPassword("");
        Map<String, Object> userData = new HashMap<>();
        userData.put("Id", user.getEmailId());
        String jwtTokenString = Jwts.builder().setClaims(userData).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, "mySecret").compact();
        tokenMap.put("token", jwtTokenString);
        tokenMap.put("message", "Login Successful");
        tokenMap.put("emailId", user.getEmailId());
        tokenMap.put("typeOfUser", user.getTypeOfUser());
        return tokenMap;
    }
}

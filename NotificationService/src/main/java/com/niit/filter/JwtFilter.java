/*
 * Author : Mushib Khan
 * Date : 11-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpServletRequest.getHeader("authorization");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new ServletException("Token Missing");

        } else {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey("mySecret").parseClaimsJws(token).getBody();
            System.out.println("Claim from token" + claims);

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

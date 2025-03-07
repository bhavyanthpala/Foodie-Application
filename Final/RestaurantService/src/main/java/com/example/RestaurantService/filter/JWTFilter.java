package com.example.RestaurantService.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("Inside JWT Filter");
        String header = request.getHeader("Authorization");
        System.out.println("Header: " + header);
        if (header == null || !header.startsWith("Bearer")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("Missing or Invalid Token");
        } else {
            String token = header.substring(7);
            System.out.println(token);
            Claims claims = Jwts.parser().setSigningKey("mySecretkey").parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
            filterChain.doFilter(request, response); //some more filters , controller}
        }
    }
}

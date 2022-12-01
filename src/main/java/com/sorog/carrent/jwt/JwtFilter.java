package com.sorog.carrent.jwt;

import com.sorog.carrent.services.jwtservices.CustomUserDetails;
import com.sorog.carrent.services.jwtservices.CustomUserDetailsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


import static org.springframework.util.StringUtils.hasText;

@Component
@Log
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    private static String currentUserLogin;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        System.out.println(token);

        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            currentUserLogin = userLogin;
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String bearer = request.getHeader(AUTHORIZATION);
        if (!hasText(bearer)) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    bearer = "Bearer ";
                    bearer += cookie.getValue();
                }
            }
        }
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public static String getCurrentUserLogin() {
        return currentUserLogin;
    }
}

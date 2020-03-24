package de.darfichraus.filters;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
@Order(Ordered.HIGHEST_PRECEDENCE)
public class APIKeyAuthFilter extends OncePerRequestFilter {
    private String authHeaderName;
    private String validApiKey;


    public APIKeyAuthFilter(String authHeaderName, String validApiKey) {
        this.authHeaderName = authHeaderName;
        this.validApiKey = validApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (!request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            if (!validApiKey.equals(request.getHeader(authHeaderName))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new BadCredentialsException("The API key was not found or not the expected value.");
            }
        }
        filterChain.doFilter(request, response);
    }

}
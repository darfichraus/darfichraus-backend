package de.darfichraus.config;

import de.darfichraus.filters.APIKeyAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${http.auth.headerName}")
    private String authHeaderName;

    @Value("${http.auth.validApiKey}")
    private String authHeaderValue;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        APIKeyAuthFilter filter = new APIKeyAuthFilter(authHeaderName, authHeaderValue);

        httpSecurity.
                antMatcher("/**")
                .csrf()
                .disable()
                .addFilterAfter(filter, BasicAuthenticationFilter.class);

    }

}
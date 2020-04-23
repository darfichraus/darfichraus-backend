package de.darfichraus.config;


import com.google.common.collect.ImmutableList;
import org.pac4j.core.authorization.authorizer.DefaultAuthorizers;
import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig {

    public static CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("API-KEY", "Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Configuration
    @Order(1)
    public static class JwtAuthConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            final SecurityFilter filter = new SecurityFilter(config, "DirectBearerAuthClient", DefaultAuthorizers.IS_FULLY_AUTHENTICATED);
            http
                    .antMatcher("/admin/**").csrf()
                    .disable()
                    .cors().configurationSource(corsConfigurationSource()).and()
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }
    }

    @Configuration
    @Order(2)
    public static class ApiKeySecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {

            SecurityFilter securityFilter = new SecurityFilter(config, "HeaderClient", DefaultAuthorizers.IS_ANONYMOUS);

            securityFilter.setMatchers("excludedPath");

            httpSecurity
                    .csrf()
                    .disable()
                    .cors().configurationSource(corsConfigurationSource()).and()
                    .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        }


    }

}
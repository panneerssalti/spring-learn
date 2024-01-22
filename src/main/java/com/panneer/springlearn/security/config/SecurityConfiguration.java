package com.panneer.springlearn.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.panneer.springlearn.security.enums.Permission.*;
import static com.panneer.springlearn.security.enums.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/actuator/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL).permitAll().
                         requestMatchers("/user1/**")
                                .hasAnyRole(USER1.name(), ALL.name()).
                                requestMatchers(GET, "/user1/**").
                                hasAnyAuthority(USER1_READ.name()).
                                requestMatchers(POST, "/user1/**").
                                hasAnyAuthority(USER1_CREATE.name()).
                                requestMatchers(PUT, "/user1/**").
                                hasAnyAuthority(USER1_UPDATE.name()).
                                requestMatchers(DELETE, "/user1/**").
                                hasAnyAuthority(USER1_DELETE.name()).
                                requestMatchers("/user2/**").
                                hasAnyRole(USER2.name(), ALL.name()).
                                requestMatchers(GET, "/user2/**")
                                .hasAnyAuthority(USER2_READ.name()).
                                requestMatchers(POST, "/user2/**").
                                hasAnyAuthority(USER2_CREATE.name()).
                                requestMatchers(PUT, "/user2/**").
                                hasAnyAuthority(USER2_UPDATE.name()).
                                requestMatchers(DELETE, "/user2/**").
                                hasAnyAuthority(USER2_DELETE.name())
                                .requestMatchers(PathRequest.toH2Console()).authenticated().anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(STATELESS)).authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).logout(logout -> logout.logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }

}

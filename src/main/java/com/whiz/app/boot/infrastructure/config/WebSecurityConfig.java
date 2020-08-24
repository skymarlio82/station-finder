package com.whiz.app.boot.infrastructure.config;

import com.whiz.app.boot.infrastructure.security.JwtConfigurer;
import com.whiz.app.boot.infrastructure.security.JwtAccessDeniedHandler;
import com.whiz.app.boot.infrastructure.security.JwtAuthenticationEntryPoint;
import com.whiz.app.boot.infrastructure.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public WebSecurityConfig(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JwtAuthenticationEntryPoint authenticationErrorHandler,
        JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // Configure BCrypt password encoder =====================================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure paths and requests that should be ignored by Spring Security ================================

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            // allow anonymous resource requests
            .antMatchers(
                "/",
                "/favicon.ico",
                "/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/h2-console/**",
                "/swagger-ui.html",
                "/v2/**",
                "/swagger-resources/**"
            );
    }

    // Configure security settings ===========================================================================

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // we don't need CSRF because our token is invulnerable
            .csrf().disable()

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

            .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)

            // enable h2-console
            .and()
                .headers()
                .frameOptions()
                .sameOrigin()

            // create no session
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
                .authorizeRequests()
                .antMatchers("/login/native").permitAll()
                .antMatchers("/authorization/callback").permitAll()

                .antMatchers("/api/user/me").hasAuthority("ROLE_USER")
                .antMatchers("/api/user/all").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/user/id/*").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/user/users").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.PUT, "/api/user/users").hasAuthority("ROLE_ADMIN")

                .anyRequest().authenticated()

            .and()
                .apply(securityConfigurerAdapter());
    }

    private JwtConfigurer securityConfigurerAdapter() {
        return new JwtConfigurer(tokenProvider);
    }
}
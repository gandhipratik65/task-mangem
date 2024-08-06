package com.banq.assign.common.security.config;

import com.banq.assign.common.filter.JwtAuthenticationFilter;
import com.banq.assign.common.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up HTTP security, authentication, and authorization.
 * Configures JWT authentication, password encoding, and access control rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    private UserDetailsService userDetailsService;



    /**
     * Bean for AuthenticationManager.
     *
     * @return AuthenticationManager instance.
     * @throws Exception if any error occurs during creation.
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/tasks").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/tasks").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/tasks/search").access("hasRole('READ') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.GET, "/api/tasks/{id}").access("hasRole('READ') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.GET, "/api/tasks").access("hasRole('READ') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.GET, "/api/tasks/history/{id}").access("hasRole('READ') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.DELETE, "/api/tasks/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/tasks").hasRole("ADMIN")

                .antMatchers("/api/tasks/**").authenticated()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        logger.info("Security configuration initialized.");

    }
    /**
     * Bean for password encoder.
     *
     * @return PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Md4PasswordEncoder();
    }
}

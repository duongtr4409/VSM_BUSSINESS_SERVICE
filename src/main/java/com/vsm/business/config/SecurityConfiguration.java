package com.vsm.business.config;

import com.vsm.business.security.AuthoritiesConstants;
import com.vsm.business.security.jwt.JWTConfigurer;
import com.vsm.business.security.jwt.TokenProvider;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import tech.jhipster.config.JHipsterProperties;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JHipsterProperties jHipsterProperties;

    private final TokenProvider tokenProvider;
    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        JHipsterProperties jHipsterProperties,
        SecurityProblemSupport problemSupport
    ) {
        this.tokenProvider = tokenProvider;
        this.problemSupport = problemSupport;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .headers()
            .contentSecurityPolicy(jHipsterProperties.getSecurity().getContentSecurityPolicy())
            .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
            .and()
            .frameOptions()
            .deny()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/oauth").permitAll()
            .antMatchers("/api/v2/oauth").permitAll()
            .antMatchers("/api/theme-configs/**").permitAll()
            .antMatchers("/api/file/office365/download/**").permitAll()
            .antMatchers("/api/logout").permitAll()
            .antMatchers("/api/re-login").permitAll()
            .antMatchers("/api/verify").permitAll()
            .antMatchers("/api/sap/_sync/**").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/health/**").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/public/**").permitAll()
            .antMatchers("/customer/api/**").permitAll()
            .and()
            .apply(securityConfigurerAdapter());
//        // DuowngTora: add ldap
//            .and().authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin();
        // @formatter:on
    }

    // DuowngTora: add ldap
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .ldapAuthentication()
//            .userDnPatterns("uid={0},ou=people")
//            .groupSearchBase("ou=groups")
//            .contextSource()
//            .url("ldap://192.168.100.251:389/dc=2bs,dc=system,dc=vn")
//            .and()
//            .passwordCompare()
//            .passwordEncoder(new BCryptPasswordEncoder())
//            .passwordAttribute("userPassword");
//    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}

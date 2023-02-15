package com.vsm.business.config;

import org.elasticsearch.common.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        if (Strings.isNullOrEmpty(authentication.getPrincipal().toString())) {
            return null;
        }
        if (Strings.isNullOrEmpty(authentication.getCredentials().toString())) {
            return null;
        }
        return null;
    }
}

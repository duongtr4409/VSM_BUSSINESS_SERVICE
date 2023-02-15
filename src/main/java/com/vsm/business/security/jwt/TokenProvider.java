package com.vsm.business.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.auth.MD5Service;
import com.vsm.business.service.auth.bo.MyUserDetail;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import liquibase.pro.packaged.E;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import tech.jhipster.config.JHipsterProperties;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private static final String AUTHORITIES_INFO = "auth_info";

    private static final String AUTHORITIES_ID = "auth_id";

    private static final String AUTHORITIES_EXPIRY = "auth_expiry";

    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    private final long tokenValidityInMillisecondsForRememberMe;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private MD5Service md5Service;

    public TokenProvider(JHipsterProperties jHipsterProperties) {
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            log.warn(
                "Warning: the JWT key used is not Base64-encoded. " +
                "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security."
            );
            secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

            // lấy id của user \\
        Long userId = null;
        try {
            MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
            userId = myUserDetail.getId();
        }catch (Exception e){};
            // end lấy id của user \\

        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(AUTHORITIES_INFO, this.convertObjectToJson(authentication.getPrincipal()))
            .claim(AUTHORITIES_ID, userId)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact();
    }

    public String createRefreshToken(String seed){
        Random random = new Random();
        return md5Service.getMD5(seed + random.nextInt() + Instant.now());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Date expiration = claims.getExpiration();
        Date now = Date.from(Instant.now());
        if(now.after(expiration)){
            return null;
        }
//        Collection<? extends GrantedAuthority> authorities = Arrays
//            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//            .filter(auth -> !auth.trim().isEmpty())
//            .map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList());

//        User principal = new User(claims.getSubject(), "", authorities);

        Collection<GrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
//        MyUserDetail principal = new MyUserDetail(claims.getSubject(), "", claims.get(AUTHORITIES_INFO, MyUserDetail.class).getUserInfo(), authorities);
        MyUserDetail principal = null;
        try {
            principal = this.objectMapper.readValue(claims.get(AUTHORITIES_INFO, String.class), MyUserDetail.class);
        } catch (JsonProcessingException e) {
            try {
                Long userId = claims.get(AUTHORITIES_ID, Long.class);
                UserInfo userInfo = this.userInfoRepository.findById(userId).get();
                principal = new MyUserDetail(userInfo.getUserName(), userInfo, null);
            }catch (Exception e1){
                log.error("getAuthentication: {}", e1);
            }
        }

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    private String convertObjectToJson(Object obj){
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.registerModule(new JSR310Module());
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}

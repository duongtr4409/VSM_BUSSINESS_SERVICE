package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.config.JwtAuthenticationManager;
import com.vsm.business.security.jwt.TokenProvider;
import com.vsm.business.service.auth.MD5Service;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.authenicate.*;
import com.vsm.business.service.custom.UserInfoCustomService;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.web.rest.errors.bo.exception.AuthorityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthenicateCustomRest {

    private final UserInfoCustomService userInfoCustomService;

    private final UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private AuthenticateUtils authenticateUtils;

    private Map<String, MyUserDetail> refreshTokens = new HashMap<>();

    private MD5Service md5Service;

    @Value("${users.mule:mule@vingroup.net}")
    private String MULE_USERNAME;

    public AuthenicateCustomRest(UserInfoCustomService userInfoCustomService, JwtUserDetailsService userDetailsService, JwtAuthenticationManager authenticationManager, TokenProvider tokenProvider, AuthenticateUtils authenticateUtils, MD5Service md5Service) {
        this.userInfoCustomService = userInfoCustomService;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.authenticateUtils = authenticateUtils;
        this.md5Service = md5Service;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationTokenDuowngTora(@RequestBody AuthenicateDTO authenicateDTO) throws Exception {
        authenticate(authenicateDTO);

        // kiểm tra xem đã đăng nhập sai quá số lần cho phép chưa
        if (!this.authenticateUtils.checkLogin(authenicateDTO.getUsername())) {
            throw new RuntimeException("Login Fail More Than Allowed");
        }

        UserInfoDTO userInfoDTO = userInfoCustomService.findByUsernameAndPassword(authenicateDTO.getUsername(), md5Service.getMD5(authenicateDTO.getPassword()));
        if (userInfoDTO == null) {
            this.authenticateUtils.updateLoginFail(authenicateDTO.getUsername());       // đăng nhập thất bại -> cập nhật thông tin số lần login fail
            return ResponseEntity.ok().body(new FailLoadMessage(null));
        }
        userInfoCustomService.createOTP(userInfoDTO);
        if(userInfoDTO.getUserName().equalsIgnoreCase(MULE_USERNAME)){
            this.authenticateUtils.resetLoginFail(userInfoDTO.getId());
            final MyUserDetail userDetails = (MyUserDetail) userDetailsService.loadUserByUsername(authenicateDTO.getUsername());
            JwtDTO jwtDTO = createAuth(userInfoDTO, userDetails, authenicateDTO.isRememberMe());
            return ResponseEntity.ok().body(new LoadedMessage(jwtDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(null));
    }

    @PostMapping("/verify")
    public ResponseEntity<IResponseMessage> verify(@RequestBody AuthenicateDTO authenicateDTO) throws Exception {
        authenticate(authenicateDTO);
        // kiểm tra xem đã đăng nhập sai quá số lần cho phép chưa
        if (!this.authenticateUtils.checkLogin(authenicateDTO.getUsername())) {
            throw new RuntimeException("Login Fail More Than Allowed");
        }
        UserInfoDTO userInfoDTO = userInfoCustomService.findByUsernameAndPassword(authenicateDTO.getUsername(), md5Service.getMD5(authenicateDTO.getPassword()));

        if (userInfoDTO == null) {
            this.authenticateUtils.updateLoginFail(authenicateDTO.getUsername());       // đăng nhập thất bại -> cập nhật thông tin số lần login fail
            return ResponseEntity.ok().body(new FailLoadMessage(null));
        }
        if(!userInfoDTO.getPasswordEncode().equals(md5Service.getMD5(authenicateDTO.getToken()))){
            this.authenticateUtils.updateLoginFail(authenicateDTO.getUsername());
            return ResponseEntity.ok().body(new FailLoadMessage(null));
        }
        if(!userInfoCustomService.verify(userInfoDTO, authenicateDTO.getToken())){
            this.authenticateUtils.updateLoginFail(authenicateDTO.getUsername());
            return ResponseEntity.ok().body(new FailLoadMessage(null));
        }
        this.authenticateUtils.resetLoginFail(userInfoDTO.getId());
        final MyUserDetail userDetails = (MyUserDetail) userDetailsService.loadUserByUsername(authenicateDTO.getUsername());
//        final String token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword()), authenicateDTO.isRememberMe());
//        final String refreshToken = tokenProvider.createRefreshToken(userDetails.getUserName());
//        refreshTokens.put(refreshToken, userDetails);
//        return ResponseEntity.ok().body(new LoadedMessage(new JwtDTO(token, refreshToken, BasicUserInfoDTO.fromUserInfoDTO(userInfoDTO))));
        JwtDTO jwtDTO = createAuth(userInfoDTO, userDetails, authenicateDTO.isRememberMe());
        return ResponseEntity.ok().body(new LoadedMessage(jwtDTO));
    }
//                              // back up h�m createAuthenticationToken \\
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenicateDTO authenicateDTO) throws Exception {
//        authenticate(authenicateDTO);
//        UserInfoDTO userInfoDTO = userInfoCustomService.findByUsernameAndPassword(authenicateDTO.getUsername(), authenicateDTO.getPassword());
//        if (userInfoDTO == null) {
//            return ResponseEntity.ok().body(new FailLoadMessage(authenicateDTO));
//        }
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenicateDTO.getUsername());
//        final String token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()), authenicateDTO.isRememberMe());
//        return ResponseEntity.ok().body(new LoadedMessage(new JwtDTO(token, token, BasicUserInfoDTO.fromUserInfoDTO(userInfoDTO))));
//    }

    @PostMapping("/oauth")
    public ResponseEntity<?> createOAuthDuowngTora(@RequestBody OAuthTokenDTO oAuthTokenDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoCustomService.findByOAuth(oAuthTokenDTO);
        if (userInfoDTO == null) {
            throw new AuthorityException();
        }
        final MyUserDetail userDetails = (MyUserDetail) userDetailsService.loadUserByUsername(userInfoDTO.getUserName());
//        final String token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword()), false);
//        final String refreshToken = tokenProvider.createRefreshToken(userDetails.getUserName());
        JwtDTO jwtDTO = createAuth(userInfoDTO, userDetails, false);
        return ResponseEntity.ok().body(new LoadedMessage(jwtDTO));
    }

//                              // back up h�m createOAuth \\
//    @PostMapping("/oauth")
//    public ResponseEntity<?> createOAuth(@RequestBody OAuthDTO oAuthDTO) throws Exception {
//        UserInfoDTO userInfoDTO = userInfoCustomService.findByOAuth(oAuthDTO);
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(oAuthDTO.getUsername());
//        final String token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()), false);
//        return ResponseEntity.ok().body(new LoadedMessage(new JwtDTO(token, token, BasicUserInfoDTO.fromUserInfoDTO(userInfoDTO))));
//    }

    @GetMapping("/logout")
    public ResponseEntity<IResponseMessage> logout(@RequestParam String resfreshToken) {
        refreshTokens.remove(resfreshToken);
        return ResponseEntity.ok().body(new LoadedMessage(null));
    }

    @GetMapping("/re-login")
    public ResponseEntity<IResponseMessage> reLogin(@RequestParam String refreshToken) throws AuthorityException {
        if(!refreshTokens.containsKey(refreshToken)){
            throw new AuthorityException();
        }
        MyUserDetail userDetail = refreshTokens.get(refreshToken);
        Optional<UserInfoDTO> userInfoDTO = userInfoCustomService.findActiveById(userDetail.getId());
        if(!userInfoDTO.isPresent()){
            throw new AuthorityException();
        }
        JwtDTO jwtDTO = createAuth(userInfoDTO.get(), userDetail, false);
        return ResponseEntity.ok().body(new LoadedMessage(jwtDTO));
    }

    private JwtDTO createAuth(UserInfoDTO userInfoDTO, MyUserDetail userDetail, boolean rememberMe) {
        final String token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword()), rememberMe);
        final String refreshToken = tokenProvider.createRefreshToken(userDetail.getUserName());
        refreshTokens.put(refreshToken, userDetail);
        return new JwtDTO(token, refreshToken, BasicUserInfoDTO.fromUserInfoDTO(userInfoDTO));
    }

    private void authenticate(AuthenicateDTO authenicateDTO) throws Exception {
        Objects.requireNonNull(authenicateDTO.getUsername());
        Objects.requireNonNull(authenicateDTO.getPassword());

        try {
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenicateDTO.getUsername(), authenicateDTO.getPassword()));

            final MyUserDetail userDetails = (MyUserDetail) userDetailsService.loadUserByUsername(authenicateDTO.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, authenicateDTO.getPassword()));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

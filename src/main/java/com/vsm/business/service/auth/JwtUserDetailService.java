package com.vsm.business.service.auth;

import com.vsm.business.common.AppConstant;
import com.vsm.business.config.Constants;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.UserInfoCustomService;
import com.vsm.business.service.dto.UserInfoDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailService implements UserDetailsService {

    private final UserInfoCustomService userInfoCustomService;

    private final GrantedAuthorityCustomService grantedAuthorityCustomService;

    public JwtUserDetailService(UserInfoCustomService userInfoCustomService, GrantedAuthorityCustomService grantedAuthorityCustomService) {
        this.userInfoCustomService = userInfoCustomService;
        this.grantedAuthorityCustomService = grantedAuthorityCustomService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserInfoDTO userInfoDTO = userInfoCustomService.findByUsernameOrEmail(username, username);
        UserInfo userInfo = userInfoCustomService.myFindByUsernameOrEmail(username, username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(AppConstant.Error.USERNAME_NOT_FOUND);
        }
        //return new User(userInfoDTO.getUserName(), userInfoDTO.getPassword(), grantedAuthorityCustomService.grantedAuthority(userInfoDTO.getRoles().stream().collect(Collectors.toList())));
        return new MyUserDetail(userInfo.getUserName(), userInfo, grantedAuthorityCustomService.myGrantedAuthority(userInfo.getRoles().stream().collect(Collectors.toList())));
    }
}

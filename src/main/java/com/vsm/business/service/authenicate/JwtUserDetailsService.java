package com.vsm.business.service.authenicate;

import com.vsm.business.domain.UserInfo;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.dto.*;
import com.vsm.business.service.custom.UserInfoCustomService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserInfoCustomService userInfoCustomService;


    public JwtUserDetailsService(UserInfoCustomService userInfoCustomService) {
        this.userInfoCustomService = userInfoCustomService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserInfoDTO userInfoDTO = this.userInfoCustomService.findByUsernameOrEmail(username, username);
        UserInfo userInfo = userInfoCustomService.myFindByUsernameOrEmail(username, username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //return new User(userInfoDTO.getUserName(), userInfoDTO.getPassword(), new ArrayList<>());
        return new MyUserDetail(userInfo.getUserName(), userInfo, new ArrayList<>());
    }

}

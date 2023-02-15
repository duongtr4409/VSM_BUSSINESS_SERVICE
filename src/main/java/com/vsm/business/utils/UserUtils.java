package com.vsm.business.utils;

import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.auth.bo.MyUserDetail;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserUtils {

    public static final String MESS_NOT_AUTHORITY = "NOT AUTHORITY";

    private final UserInfoRepository userInfoRepository;

    private final ConditionUtils conditionUtils;

    public UserUtils(UserInfoRepository userInfoRepository, ConditionUtils conditionUtil) {
        this.userInfoRepository = userInfoRepository;
        this.conditionUtils = conditionUtil;
    }

    public MyUserDetail getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof MyUserDetail){
            MyUserDetail myUserDetail = (MyUserDetail) principal;
            return myUserDetail;
        }else if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            if(Strings.isNullOrEmpty(email)) return null;
            List<UserInfo> userInfos = this.userInfoRepository.findAllByEmail(email);
            if(userInfos != null && !userInfos.isEmpty()){
                if(userInfos.size() == 1) return new MyUserDetail(userInfos.get(0).getUserName(), userInfos.get(0), null);
                else {
                    UserInfo userInfo = userInfos.stream().filter(ele -> email.equals(ele.getUserName()) && this.conditionUtils.checkTrueFalse(ele.getIsActive()) && !this.conditionUtils.checkDelete(ele.getIsDelete())).findFirst().get();
                    return new MyUserDetail(userInfo.getUserName(), userInfo, null);
                }
            }else{
                userInfos = this.userInfoRepository.findAllByUserName(email);
                if(userInfos != null && !userInfos.isEmpty()){
                    if(userInfos.size() == 1) return new MyUserDetail(userInfos.get(0).getUserName(), userInfos.get(0), null);
                    else{
                        UserInfo userInfo = userInfos.stream().filter(ele -> email.equals(ele.getUserName()) && this.conditionUtils.checkTrueFalse(ele.getIsActive()) && !this.conditionUtils.checkDelete(ele.getIsDelete())).findFirst().get();
                        return new MyUserDetail(userInfo.getUserName(), userInfo, null);
                    }
                }
                return null;
            }
        }else{
            return null;
        }
    }

}

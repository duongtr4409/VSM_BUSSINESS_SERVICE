package com.vsm.business.service.auth;

import com.vsm.business.domain.Role;
import com.vsm.business.service.dto.RoleDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrantedAuthorityCustomService {
    public List<GrantedAuthority> grantedAuthority(List<RoleDTO> roleDTOS) {
        List<GrantedAuthority> result = new ArrayList<>();
        for (RoleDTO roleDTO :
            roleDTOS) {
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return roleDTO.getRoleName();
                }
            };
        }
        return result;
    }

    public List<GrantedAuthority> myGrantedAuthority(List<Role> roleDTOS) {
        List<GrantedAuthority> result = new ArrayList<>();
        for (Role roleDTO :
            roleDTOS) {
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return roleDTO.getRoleName();
                }
            };
        }
        return result;
    }
}

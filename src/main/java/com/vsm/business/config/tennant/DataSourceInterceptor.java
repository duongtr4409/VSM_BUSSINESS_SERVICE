package com.vsm.business.config.tennant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String tenant = request.getHeader("tenant");
        if(tenant != null){
            TenantContextHolder.setTenant(tenant);
        }else{
            if(!request.getRequestURL().toString().contains("localhost")){
                response.getWriter().println("missing tenant");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }
}

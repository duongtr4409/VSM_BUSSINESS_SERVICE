package com.vsm.business.config.tennant;

import com.vsm.business.domain.Tennant;
import com.vsm.business.repository.TennantRepository;
import com.vsm.business.service.TennantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Component
public class TenantHelper {

    @Value("${TenantServiceUrl:http://localhost:3000/tenantConfig/}")
    private String tenantServiceUrl;

    public Tennant[] getAllTenant(){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("", "");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = buildRest();

        ResponseEntity<Tennant []> response
            = restTemplate.getForEntity(tenantServiceUrl, Tennant[].class);
        return response.getBody();
    }

    private RestTemplate buildRest() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        requestFactory.setReadTimeout(8 * 10000);
        requestFactory.setConnectTimeout(8 * 10000);
        return restTemplate;
    }
}

package com.vsm.business.utils;

//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class CallAPIUtils {

    private final Logger log = LoggerFactory.getLogger(CallAPIUtils.class);

    @Value("${mule.USE-PROXY:TRUE}")
    public String USE_PROXY;

    @Value("${mule.PROXY-SERVER-HOST:http://10.111.127.200}")
    public String PROXY_SERVER_HOST;

    @Value("${mule.PROXY-SERVER-PORT:9090}")
    public String PROXY_SERVER_PORT;

    public ObjectMapper objectMapper = new ObjectMapper();

    public <T> T createRestTemplate(String uri, Object body, HttpMethod method, Map<String, Object> params, Class<T> clz) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue())) {
                    uriBuilder.queryParam(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        uri = uriBuilder.build().encode().toUriString();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(converter);
        RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
                return (
                    httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR);
            }

            @Override
            public void handleError(ClientHttpResponse httpResponse) throws IOException {
                StringWriter writer = new StringWriter();
                IOUtils.copy(httpResponse.getBody(), writer, StandardCharsets.UTF_8.toString());
                String theString = writer.toString();
                log.error("ERROR:>>>>>>>>>>>>> {}", theString);
                writer.close();
                httpResponse.getBody().close();
                httpResponse.close();
            }
        }).build();

        restTemplate.setMessageConverters(converters);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        ResponseEntity<T> response = restTemplate.exchange(uri, method, entity, clz);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.format("%s not found: %s", "ERROR:", response.getBody()));
        }
        return response.getBody();
    }

    public <T> T createRestTemplate_v2(String uri, Object body, HttpMethod method, Map<String, Object> params, Class<T> clz) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        uri = uriBuilder.build().encode().toUriString();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);

        RestTemplate restTemplate;
        if ("TRUE".equalsIgnoreCase(this.USE_PROXY)) {
            String PROXY_SERVER_HOST_TEMP = this.PROXY_SERVER_HOST;
            int PROXY_SERVER_PORT_TEMP = Integer.parseInt(this.PROXY_SERVER_PORT);
            restTemplate = new RestTemplateBuilder(new RestTemplateCustomizer() {
                @Override
                public void customize(RestTemplate restTemplate) {

                    HttpHost proxy = new HttpHost(PROXY_SERVER_HOST_TEMP, PROXY_SERVER_PORT_TEMP);
                    HttpClient httpClient = HttpClientBuilder.create()
                        .setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {
                            @Override
                            public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
                                return super.determineProxy(target, request, context);
                            }
                        })
                        .build();
                    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
                }
            }).errorHandler(new ResponseErrorHandler() {
                @Override
                public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
                    return (
                        httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR
                            || httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR);
                }

                @Override
                public void handleError(ClientHttpResponse httpResponse) throws IOException {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(httpResponse.getBody(), writer, StandardCharsets.UTF_8.toString());
                    String theString = writer.toString();
                    log.error("ERROR:>>>>>>>>>>>>> {}", theString);
                    writer.close();
                    httpResponse.getBody().close();
                    httpResponse.close();
                }
            }).build();
        } else {
            restTemplate = new RestTemplateBuilder().errorHandler(new ResponseErrorHandler() {
                @Override
                public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
                    return (
                        httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR
                            || httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR);
                }

                @Override
                public void handleError(ClientHttpResponse httpResponse) throws IOException {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(httpResponse.getBody(), writer, StandardCharsets.UTF_8.toString());
                    String theString = writer.toString();
                    log.error("ERROR:>>>>>>>>>>>>> {}", theString);
                    writer.close();
                    httpResponse.getBody().close();
                    httpResponse.close();
                }
            }).build();
        }

        restTemplate.setMessageConverters(converters);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue())) {
                    //headers.add(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }

        String bodyString = new Gson().toJson(body);

        HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);

        ResponseEntity<T> response = restTemplate.exchange(uri, method, entity, clz);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.format("%s not found: %s", "ERROR:", response.getBody()));
        }
        return response.getBody();
    }


    public <T> T createRestTemplateCallSAP(String url, Object body, HttpMethod method, Map<String, Object> params, Class<T> clz) throws Exception {
        RestTemplate restTemplate;

        if ("TRUE".equalsIgnoreCase(this.USE_PROXY)) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_SERVER_HOST, Integer.valueOf(PROXY_SERVER_PORT)));
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setProxy(proxy);
            restTemplate = new RestTemplate(requestFactory);
        } else {
            restTemplate = new RestTemplate();
        }

        FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        formConverter.setCharset(Charset.forName("UTF8"));
        restTemplate.getMessageConverters().add(formConverter);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        URI uri = new URI(url);
        String requestBody = new Gson().toJson(body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue())) {
                    headers.add(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);

        try {
            log.info("requestBody:", requestBody);
            log.info("headers", new Gson().toJson(headers));
            log.info("httpEntity", new Gson().toJson(httpEntity));
        }catch (Exception e) {log.error("{}", e);}

        ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, httpEntity, clz);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.format("%s not found: %s", "ERROR:", responseEntity.getBody()));
        }
        return responseEntity.getBody();
    }

    public <T> T createRestTemplateGraph(String url, Object body, HttpMethod method, Map<String, Object> params, Class<T> clz) throws Exception {
        RestTemplate restTemplate;
        restTemplate = new RestTemplate();

        FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        formConverter.setCharset(Charset.forName("UTF8"));
        restTemplate.getMessageConverters().add(formConverter);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        URI uri = new URI(url);
        String requestBody = new Gson().toJson(body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue())) {
                    headers.add(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, httpEntity, clz);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.format("%s not found: %s", "ERROR:", responseEntity.getBody()));
        }
        return responseEntity.getBody();
    }


    public <T> T createRestTemplateUploadFile(String uri, Object body, File file, HttpMethod method, Map<String, Object> params, Class<T> clz) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        uri = uriBuilder.build().encode().toUriString();
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        if (file != null) bodyMap.add("file", new FileSystemResource(file));
        if (params != null) params.keySet().forEach(ele -> bodyMap.add(ele, params.get(ele)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<T> response = template.exchange(uri, method, requestEntity, clz);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.format("%s not found: %s", "ERROR:", response.getBody()));
        }
        return response.getBody();
    }

    public <T> ResponseEntity createRestTemplateDownloadFile(String uri, Object body, HttpMethod method, Map<String, Object> params, Class<T> clz) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (!ObjectUtils.isEmpty(entry.getValue())) {
                    String key = entry.getKey();
                    uriBuilder.queryParam(key, value);
                }
            }
        }
        uri = uriBuilder.build().encode().toUriString();
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
//        if(params != null) params.keySet().forEach(ele -> bodyMap.add(ele, params.get(ele)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity requestEntity = new HttpEntity(bodyMap, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<T> response = template.exchange(uri, method, requestEntity, clz);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.format("%s not found: %s", "ERROR:", response.getBody()));
        }
        return response;
    }

//    public <T> BaseClientRp<T> callAPICreateFolder(String url, Object body, Class<T> clz){
//        BaseClientRp responJson = null;
//        try {
//            URI upLoadUrl = new URI(url);
//            Gson gson = new Gson();
//            System.out.println(gson.toJson(body));
//            RestTemplate restTemplate = new RestTemplate();
//            FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
//            formConverter.setCharset(Charset.forName("UTF8"));
//            restTemplate.getMessageConverters().add(formConverter);
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            ResponseEntity responseEntity = restTemplate.postForEntity(upLoadUrl, body, BaseClientRp.class);
//            responJson = (BaseClientRp) responseEntity.getBody();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return responJson;
//    }


    public <T> T createRestTemplateFile(String uri, Object body, HttpMethod httpMethod, Map<String, Object> params,
                                        Class<T> clz) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue()))
                    builder.queryParam(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        uri = builder.build().encode().toString();
        System.out.println(uri);
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        restTemplate.setMessageConverters(converters);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> response = restTemplate.exchange(uri, httpMethod, entity, clz);
        if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
            throw new Exception(String.format("ERROR: %s", response.getBody()));
        }
        return response.getBody();
    }

    public <T> T createRestTemplateFolder(String uri, Object body, HttpMethod httpMethod, Map<String, Object> params,
                                          Class<T> clz) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue()))
                    builder.queryParam(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        uri = builder.build().encode().toString();
        System.out.println(uri);
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        restTemplate.setMessageConverters(converters);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> response = restTemplate.exchange(uri, httpMethod, entity, clz);
        if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
            throw new Exception(String.format("ERROR: %s", response.getBody()));
        }
        return response.getBody();
    }


    /**
     * @param uri        : đường dẫn
     * @param body       : body (form-data)
     * @param httpMethod : method
     * @param params     : params (tham số)
     * @param clz        : class muốn nhận về
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T createRestTemplateMailService(String uri, Object body, List<File> files, HttpMethod httpMethod, Map<String, Object> params,
                                               Class<T> clz) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        if (files != null) files.forEach(ele -> {
            form.add("files", new FileSystemResource(ele));
        });

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (!ObjectUtils.isEmpty(entry.getValue())) {
                    if (entry.getValue().getClass() == ArrayList.class) {
                        ((List) entry.getValue()).stream().forEach(ele -> form.add(entry.getKey(), String.valueOf(ele)));
                    } else if (entry.getValue().getClass() == HashMap.class) {
                        ((HashMap) entry.getValue()).forEach((key, value) -> form.add(String.valueOf(entry.getKey()) + "[\"" + String.valueOf(key) + "\"]", String.valueOf(value)));
                    } else {
                        form.add(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
            }
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, headers);

        String serverUrl = uri;

        ArrayList<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(
            Arrays.asList(new MappingJackson2HttpMessageConverter(), new ResourceHttpMessageConverter(), new FormHttpMessageConverter()));

        RestTemplate restTemplate = new RestTemplate(converters);

        ResponseEntity<T> response = restTemplate.postForEntity(serverUrl, requestEntity, clz);

        if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
            throw new Exception(String.format("ERROR: %s", response.getBody()));
        }
        return response.getBody();
    }
}

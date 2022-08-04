package kr.or.lx.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService<T> {
 
    private RestTemplate restTemplate;
 
    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
 
    public ResponseEntity<T> get(String url) {
        return callApi(url, HttpMethod.GET, null, (Class<T>)Object.class, MediaType.APPLICATION_JSON);
    }
 
    public ResponseEntity<T> get(String url, Class<T> clazz) {
        return callApi(url, HttpMethod.GET, null, clazz, MediaType.APPLICATION_JSON);
    }
 
    public ResponseEntity<T> post(String url, Object body) {
        return callApi(url, HttpMethod.POST, body,(Class<T>)Object.class, MediaType.APPLICATION_JSON);
    }
 
    public ResponseEntity<T> post(String url, Object body, Class<T> clazz) {
        return callApi(url, HttpMethod.POST, body, clazz, MediaType.APPLICATION_JSON);
    }
    
    public ResponseEntity<T> post(String url, Object body, Class<T> clazz, MediaType mediaType) {
        return callApi(url, HttpMethod.POST, body, clazz, mediaType);
    }
    
    public ResponseEntity<T> patch(String url, Object body) {
        return callApi(url, HttpMethod.PATCH, body,(Class<T>)Object.class, MediaType.APPLICATION_JSON);
    }
 
    public ResponseEntity<T> patch(String url, Object body, Class<T> clazz) {
        return callApi(url, HttpMethod.PATCH, body, clazz, MediaType.APPLICATION_JSON);
    }
    
    public ResponseEntity<T> delete(String url, Object body) {
        return callApi(url, HttpMethod.DELETE, body,(Class<T>)Object.class, MediaType.APPLICATION_JSON);
    }
 
    public ResponseEntity<T> delete(String url, Object body, Class<T> clazz) {
        return callApi(url, HttpMethod.DELETE, body, clazz, MediaType.APPLICATION_JSON);
    }
    
    public ResponseEntity<T> post(String url, MultiValueMap<String, Object> body) {
        return multiCallApi(url, HttpMethod.POST, body,(Class<T>)Object.class, MediaType.MULTIPART_FORM_DATA);
    }
 
    public ResponseEntity<T> post(String url, MultiValueMap<String, Object> body, Class<T> clazz) {
        return multiCallApi(url, HttpMethod.POST, body, clazz, MediaType.MULTIPART_FORM_DATA);
    }
    
    public ResponseEntity<T> post(String url, MultiValueMap<String, Object> body, Class<T> clazz, MediaType mediaType) {
        return multiCallApi(url, HttpMethod.POST, body, clazz, mediaType);
    }    
    
    @SuppressWarnings("unchecked")
	private ResponseEntity<T> callApi(String url, HttpMethod httpMethod, Object body, Class<T> clazz, MediaType mediaType) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(mediaType);
    	
    	log.info("body : "+body);    	
    	ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<>(body, httpHeaders), clazz);
		log.info("getStatusCode() : "+responseEntity.getStatusCode());
		log.info("getBody() : "+responseEntity.getBody());    	
    	
        return (ResponseEntity<T>) responseEntity;
    }
    
    @SuppressWarnings("unchecked")
    private ResponseEntity<T> multiCallApi(String url, HttpMethod httpMethod, MultiValueMap<String, Object> body, Class<T> clazz, MediaType mediaType) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(mediaType);
    	
    	log.info("body : "+body);    	
    	HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);    	
    	ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestEntity, clazz);
    	log.info("getStatusCode() : "+responseEntity.getStatusCode());
    	log.info("getBody() : "+responseEntity.getBody());    	
    	return (ResponseEntity<T>) responseEntity;
    }
}


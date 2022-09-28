package kr.or.lx.login.service;

import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
	
	@Cacheable(value="userMenuCache", key="#map")
	public String getLoginUser(String url, String sessionId, Map<String, Object> map) throws Exception{
		
		HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	httpHeaders.set("Cookie", "JSESSIONID="+sessionId);
		
		RestTemplate restTemplate = new RestTemplate();
    	
    	ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(map, httpHeaders), String.class);
		
		String body = responseEntity.getBody();
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		Map<String, Object> mapper = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
		return body;
	}
	
	@CacheEvict(value="userMenuCache", allEntries=true)
	public void clearLoginUserCache() {
		
	}
	
}

package kr.or.lx.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.lx.common.ApiService;
import kr.or.lx.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class LoginController {
	
	@Value("${login.api.url}")
    private String loginApiUrl;
	
	@Autowired
	private LoginService loginService;

	//로그인 계정 받기
	@ResponseBody
	@PostMapping("/postLogin")
	public void postLogin(@RequestBody Map<String, Object> param, ModelMap model, HttpServletRequest request) throws Exception{
		log.info("postLogin");
		
		String url = loginApiUrl+"/korea-admin-1.0.0/api/ko-user/get";
		String userId = param.get("userId").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", userId);
		
//		System.out.println(map.get("account"));
		
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
    	
		Map<String, Object> mapper = loginService.getLoginUser(url, session.getId(), map);
		
		if(mapper.get("data") != null) {
//			System.out.println(mapper.get("data").toString());
			session.setAttribute("userId1", userId);
		}
	}
	
	//회원메뉴
	@ResponseBody
	@PostMapping("/getMenuByAccount")
	public String getMenuByAccount(ModelMap model, HttpServletRequest request) throws Exception{
		log.info("getMenuByAccount");
		
		String url = loginApiUrl+"/korea-admin-1.0.0/api/ko-user/getMenuByAccount";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		
		map.put("account", session.getAttribute("userId"));
		
		System.out.println(session.getAttribute("userId1"));
		
		HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	httpHeaders.set("Cookie", "JSESSIONID="+session.getId());
    	
    	RestTemplate restTemplate = new RestTemplate();
    	
    	ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(map, httpHeaders), String.class);
		
		String body = responseEntity.getBody();
		
		return body;
		
	}
	
}

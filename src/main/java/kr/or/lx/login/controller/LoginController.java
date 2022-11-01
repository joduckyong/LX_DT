package kr.or.lx.login.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.lx.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableCaching
@Controller
public class LoginController {
	
	@Value("${login.api.url}")
    private String loginApiUrl;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/loginTest")
	public String loginTest(ModelMap model, @RequestParam String userMenuId) throws Exception{
		
		model.addAttribute("userId", userMenuId);
		
		return "login/loginTest";
	}	

	//로그인 계정 받기
	@ResponseBody
	@PostMapping("/postLogin")
	public PrintWriter postLogin(@RequestBody Map<String, Object> param, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("postLogin");
		
		String url = loginApiUrl+"/korea-admin-1.0.0/api/ko-user/get";
		String userId = param.get("userId").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", userId);
		
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
    	
		loginService.clearLoginUserCache();
		String body = loginService.getLoginUser(url, session.getId(), map);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> mapper = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
		
		if(mapper.get("data") != null) {
			session.setAttribute("userId", userId);
		}else {
			session.setAttribute("userId", "noUser");
		}
		
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
	    response.setDateHeader("Expires",0);
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<script>window.location.href='/'</script>");
        out.flush(); 
        
		return out;
	}
	
	//회원메뉴
	@ResponseBody
	@PostMapping("/getMenuByAccount")
	public String getMenuByAccount(ModelMap model, HttpServletRequest request) throws Exception{
		log.info("getMenuByAccount");
		
		String url = loginApiUrl+"/korea-admin-1.0.0/api/ko-user/getMenuByAccount";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		map.put("account", userId);
		map.put("sysType", 1);
		
		String body = "";
		
		
		if("noUser".equals(userId)) {	//사용자 없을 경우
			body = "{\"data\":\"noUser\"}";
		}else if("noLogin".equals(userId)){
			body = "{\"data\":\"noLogin\"}";
		}else {
			body = loginService.getLoginUser(url, session.getId(), map);
		}
		
		return body;
		
	}
	
	//로그인 계정 받기
	@GetMapping("/noLogin")
	public String noLogin(ModelMap model, HttpSession session) throws Exception{
		log.info("noLogin");
		
		session.setAttribute("userId", "noLogin");
		
		return "/";
	}
	
}

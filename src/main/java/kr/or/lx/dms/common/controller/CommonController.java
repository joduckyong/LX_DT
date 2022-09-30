package kr.or.lx.dms.common.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.lx.common.ApiService;
import lombok.extern.slf4j.Slf4j;


@RequestMapping("common")
@Slf4j
@Controller
public class CommonController {
	
    @Value("${dms.api.url}")
    private String dmsApiUrl;
    
    @Value("${linkTarget.api.url}")
    private String linkTargetApiUrl;
    
    @Value("${login.userId}")
    private String nonUserId;

	@Autowired
	private ApiService<?> apiService;
	
	//공통코드
	@ResponseBody
	@PostMapping("{apiId}")
	public Object commonCode(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		log.info("commonCode");
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == null) {
			userId = nonUserId;
		}
		
		String url = dmsApiUrl+param.get("url");
		param.put("user_id", String.valueOf(userId));
		
		ResponseEntity<?> responseEntity = apiService.get(url);
		Object object = responseEntity.getBody();
		
		return object;
	}
	
	//연계대상 관리 공통코드
	@ResponseBody
	@PostMapping("/linkTarget/{apiId}")
	public Object linkTargetCommonCode(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		log.info("linkTargetCommonCode");
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == null) {
			userId = nonUserId;
		}
		
		String url = linkTargetApiUrl+param.get("url");
		param.put("userId", String.valueOf(userId));
		
		ResponseEntity<?> responseEntity = apiService.get(url);
		Object object = responseEntity.getBody();
		
		return object;
	}
	
}

package kr.or.lx.dms.systemMgmt.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.lx.common.ApiService;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("systemMgmt")
@Slf4j
@Controller
public class SystemMgmtController {
	
    @Value("${dms.api.url}")
    private String dmsApiUrl;
    
    @Value("${login.userId}")
    private String nonUserId;
    
	@Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/codeList")
	public String codeList(ModelMap model) throws Exception{
		
		
		return "dms/systemMgmt/codeList";
	}	
	
	@GetMapping("/classificationMgmt")
	public String classificationMgmt(ModelMap model) throws Exception{
		
		
		return "dms/systemMgmt/classificationMgmt";
	}	
	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object systemMgmtApi(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		log.info("systemMgmtApi");
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == "noLogin") {
			userId = nonUserId;
		}
		
		String url = dmsApiUrl+param.get("url");
		param.put("user_id", String.valueOf(userId));
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
}

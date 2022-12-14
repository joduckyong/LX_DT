package kr.or.lx.sandbox.info.controller;

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


@RequestMapping("sandbox/info/infoAnalysisModel")
@Slf4j
@Controller
public class InfoAnalysisModelController {
	
    @Value("${sandbox.api.url}")
    private String sandboxApiUrl;	
    
	@Value("${login.userId}")
    private String nonUserId;
	
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * 분석 모델 정보 관리 - 데이터 분석 모델
     * @return
     */
	@GetMapping("/list")
	public String infoAnalysisModel(ModelMap model) throws Exception{
		
		return "sandbox/info/infoAnalysisModel/list";
	}
	
	/**
     * 분석 모델 API
     * @return
     */		
	@ResponseBody
	@PostMapping("{apiId}")
	public Object analysisModelsBasicInfo(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == "noLogin") {
			userId = nonUserId;
		}
		
		String url = sandboxApiUrl+param.get("url");
		param.put("user_id", String.valueOf(userId));	
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
	
}

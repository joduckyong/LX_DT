package kr.or.lx.dms.systemMgmt.controller;

import java.util.Map;

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
	public Object systemMgmtApi(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		log.info("systemMgmtApi");
		
		String url = dmsApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
}

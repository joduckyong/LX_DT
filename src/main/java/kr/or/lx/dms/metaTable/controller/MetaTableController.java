package kr.or.lx.dms.metaTable.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.lx.common.ApiService;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("metaTable")
@Slf4j
@Controller
public class MetaTableController {
	
    @Value("${dms.api.url}")
    private String dmsApiUrl;	
    
    @Value("${login.userId}")
    private String nonUserId;
    
	@Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/list")
	public String list(ModelMap model) throws Exception{
		
		return "dms/metaTable/list";
	}	
	
	@GetMapping("/metaInfo")
	public String metaInfo(ModelMap model) throws Exception{
		
		return "dms/metaTable/metaInfo";
	}
	
	@GetMapping("/columnInfo/{tableIdntfcId}")
	public String columnInfo(@PathVariable String tableIdntfcId, ModelMap model) throws Exception{
		
		model.addAttribute("table_idntfc_id", tableIdntfcId);
		
		return "dms/metaTable/columnInfo";
	}
	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object metaTableApi(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		log.info("metaTableApi");
		
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

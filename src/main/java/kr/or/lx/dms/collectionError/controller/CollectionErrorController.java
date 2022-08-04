package kr.or.lx.dms.collectionError.controller;

import java.util.Map;

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

@RequestMapping("collectionError")
@Slf4j
@Controller
public class CollectionErrorController {
	
    @Value("${dms.api.url}")
    private String dmsApiUrl;	
    
	@Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/list")
	public String list(ModelMap model) throws Exception{
		
		
		return "dms/collectionError/list";
	}	
	
	@GetMapping("/detail/{instanceId}")
	public String detail(@PathVariable String instanceId, ModelMap model) throws Exception{
		
		model.addAttribute("instance_id", instanceId);
		
		return "dms/collectionError/detail";
	}	
	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object collectionErrorApi(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		log.info("collectionErrorApi");
		
		String url = dmsApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
}

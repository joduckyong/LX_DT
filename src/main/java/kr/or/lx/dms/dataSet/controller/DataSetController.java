package kr.or.lx.dms.dataSet.controller;

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

@RequestMapping("dataSet")
@Slf4j
@Controller
public class DataSetController {
	
    @Value("${dms.api.url}")
    private String dmsApiUrl;	
    
	@Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/list")
	public String list(ModelMap model) throws Exception{
		
		
		return "dms/dataSet/list";
	}	
	
	@GetMapping("/detail")
	public String detail(ModelMap model) throws Exception{
		
		
		return "dms/dataSet/detail";
	}	
	
	@GetMapping("/metaDetail")
	public String metaDetail(ModelMap model) throws Exception{
		
		
		return "dms/dataSet/metaDetail";
	}	
	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object dataSetApi(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		log.info("dataSetApi");
		
		String url = dmsApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
}

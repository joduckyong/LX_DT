package kr.or.lx.sandbox.result.controller;

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


@RequestMapping("sandbox/result/analysisResult")
@Slf4j
@Controller
public class AnalysisResultController {
	
    @Value("${sandbox.api.url}")
    private String sandboxApiUrl;	
    
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * 데이터 분석 결과 조회
     * @return
     */
	@GetMapping("/list/{sandbox_id}")
	public String analysisResult(@PathVariable String sandbox_id, ModelMap model) throws Exception{
		
		model.put("sandbox_id", sandbox_id);		
		return "sandbox/result/analysisResult/list";
	}
	
	/**
     * 샌드박스 분석결과 
     * @return
     */	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object analysisResultApi(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		
		String url = sandboxApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}
	
}

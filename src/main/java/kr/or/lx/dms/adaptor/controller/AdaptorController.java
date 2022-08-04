package kr.or.lx.dms.adaptor.controller;

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

@RequestMapping("adaptor")
@Slf4j
@Controller
public class AdaptorController {
	
    @Value("${dms.api.url}")
    private String dmsApiUrl;	
    
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * Adapter 관리 > Adapter 설정 
     * @return
     */	
	@GetMapping("/config")
	public String list(ModelMap model) throws Exception{
		
		
		return "dms/adaptor/config";
	}	
	
	/**
     * Adapter 관리 > Adapter 상세 
     * @return
     */	
	@GetMapping("/detail/{adapter_id}")
	public String detail(@PathVariable String adapter_id, ModelMap model) throws Exception{
		
		model.put("adapter_id", adapter_id);		
		return "dms/adaptor/detail";
	}	
	
	/**
     * Adapter 관리 > 접속설정
     * @return
     */	
	@GetMapping("/connectionSet/{adapter_id}/{instance_id}")
	public String connectionSet(@PathVariable String adapter_id, @PathVariable String instance_id, ModelMap model) throws Exception{
		
		model.put("adapter_id", adapter_id);	
		model.put("instance_id", instance_id);	
		return "dms/adaptor/connectionSet";
	}	
	
	/**
     * Adapter 관리 > 매칭정보
     * @return
     */	
	@GetMapping("/machingInfo/{adapter_id}/{instance_id}")
	public String machingInfo(@PathVariable String adapter_id, @PathVariable String instance_id, ModelMap model) throws Exception{
		
		model.put("adapter_id", adapter_id);	
		model.put("instance_id", instance_id);			
		return "dms/adaptor/machingInfo";
	}	
	
	/**
     * Adapter 관리 > Adapter 운영
     * @return
     */	
	@GetMapping("/operate/{adapter_id}")
	public String operate(@PathVariable String adapter_id, ModelMap model) throws Exception{
		
		model.put("adapter_id", adapter_id);	
		return "dms/adaptor/operate";
	}	
	
	/**
     * Adapter 관리 > Instance 조회
     * @return
     */	
	@GetMapping("/instance")
	public String instance(ModelMap model) throws Exception{
		
		
		return "dms/adaptor/instance";
	}	
	
	/**
     * Adaptor 관리 > 접속유형 항목관리
     * @return
     */	
	@GetMapping("/connectionType")
	public String connectionType(ModelMap model) throws Exception{
		
		return "dms/adaptor/connectionType";
	}	
	
	/**
     * Adaptor 관리 > Adaptor 유형관리
     * @return
     */	
	@GetMapping("/type")
	public String type(ModelMap model) throws Exception{
		
		
		return "dms/adaptor/type";
	}	
	
	/**
     * Adapotr 관리 > 전처리 목록
     * @return
     */	
	@GetMapping("/preProcessList")
	public String preProcessList(ModelMap model) throws Exception{
		
		
		return "dms/adaptor/preProcessList";
	}	
	
	/**
     * Adapotr 관리 > 전처리 등록
     * @return
     */	
	@GetMapping("/preProcessAdd")
	public String preProcessAdd(ModelMap model) throws Exception{
		
		
		return "dms/adaptor/preProcessAdd";
	}	
	
	/**
     * Adapotr 관리 > 전처리 상세
     * @return
     */	
	@GetMapping("/preProcessDetail/{pre_id}")
	public String preProcessDetail(@PathVariable String pre_id, ModelMap model) throws Exception{
		
		model.put("pre_id", pre_id);	
		return "dms/adaptor/preProcessDetail";
	}	
	
	/**
     * Adapotr 관리 API
     * @return
     */
	@ResponseBody
	@PostMapping("{apiId}")
	public Object adaptorApi(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		
		String url = dmsApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
}

package kr.or.lx.dms.dataMgmt.controller;

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

@RequestMapping("dataMgmt")
@Slf4j
@Controller
public class DataMgmtController {
	
    @Value("${linkTarget.api.url}")
    private String apiUrl;	
    
    @Value("${login.userId}")
    private String nonUserId;
    
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * 데이터관리 > Match Key
     * @return
     */	
	@GetMapping("/matchKey")
	public String matchKey(ModelMap model) throws Exception{
		
		return "dms/dataMgmt/matchKey";
	}	
	
	/**
	 * 데이터관리 > table
	 * @return
	 */	
	@GetMapping("/table")
	public String table(ModelMap model) throws Exception{
		
		return "dms/dataMgmt/table";
	}	
	
	
	/**
     * 데이터관리 관리 API
     * @return
     */
	@ResponseBody
	@PostMapping("{apiId}")
	public Object dataMgmtApi(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == null) {
			userId = nonUserId;
		}
		
		String url = apiUrl+param.get("url");
		param.put("userId", String.valueOf(userId));
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
}

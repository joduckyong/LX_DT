package kr.or.lx.dms.linkTarget.controller;

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

@RequestMapping("linkTarget")
@Slf4j
@Controller
public class LinkTargetController {
	
    @Value("${linkTarget.api.url}")
    private String apiUrl;	
    
    @Value("${linkTarget.target.url}")
    private String targetUrl;	
    
    @Value("${login.userId}")
    private String nonUserId;
    
	@Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/list")
	public String list(ModelMap model) throws Exception{
		
		model.addAttribute("targetUrl", targetUrl);
		
		return "dms/linkTarget/list";
	}
	
	@GetMapping("/add")
	public String add(ModelMap model) throws Exception{
		
		return "dms/linkTarget/add";
	}
	
	@GetMapping("/update/{dstbId}")
	public String update(@PathVariable String dstbId, ModelMap model) throws Exception{
		
		model.addAttribute("dstbId", dstbId);
		
		return "dms/linkTarget/update";
	}	
	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object linkTargetApi(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		log.info("dataSetApi");
		
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

package kr.or.lx.server;

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


@RequestMapping("server/receive")
@Slf4j
@Controller
public class ReceiveController {
	
	@Value("${agent.server.api.url}")
    private String agentApiUrl;    
	
    @Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/list")
	public String receiveList(ModelMap model) throws Exception{
		log.info("receiveList");
		
		return "server/receive/list";
	}
	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object receivePost(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		log.info("receivePost");
		
		String url = agentApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		if(object == null) {
			object = responseEntity.getStatusCode();
		}
		
		return object;
	}

	
}

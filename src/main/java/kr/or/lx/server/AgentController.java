package kr.or.lx.server;

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


@RequestMapping("server/agent")
@Slf4j
@Controller
public class AgentController {
	
	@Value("${agent.server.api.url}")
    private String agentApiUrl;    
	
    @Autowired
	private ApiService<?> apiService;
	
	@GetMapping("/list")
	public String agent(ModelMap model) throws Exception{
		
		return "server/agent/list";
	}
	
	@GetMapping("/add")
	public String agentAdd(ModelMap model) throws Exception{
		
		return "server/agent/add";
	}
	
	@GetMapping("/detail/{code}")
	public String agentDetail(@PathVariable String code, ModelMap model) throws Exception{
		
		model.put("code", code);		
		return "server/agent/detail";
	}

	@ResponseBody
	@PostMapping("{apiId}")
	public Object agentPost(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		
		String url = agentApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		if(object == null) {
			object = responseEntity.getStatusCode();
		}
		
		return object;
	}

	
}

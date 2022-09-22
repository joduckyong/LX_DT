package kr.or.lx.server.common.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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


@RequestMapping("server/common")
@Slf4j
@Controller
public class ServerCommonController {
	
	@Value("${agent.server.api.url}")
    private String agentApiUrl;    
	
    @Value("${server.file.path}")
    private String serverFilePath;	
    
    @Autowired
	private ApiService<?> apiService;
    
	
	@Autowired
    private RestTemplateBuilder restTemplate;
	
	//공통코드
	@ResponseBody
	@PostMapping("{apiId}")
	public Object commonCode(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		log.info("commonCode");
		
		String url = agentApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.get(url);
		Object object = responseEntity.getBody();
		
		return object;
	}		
	
	/**
	 * 다운로드
	 * @return
	 */	
	@ResponseBody
	@PostMapping("/download/{apiId}")
    public Object downloadFile(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{

		String url = agentApiUrl+param.get("url");
		
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.build().exchange(url, HttpMethod.POST, entity, byte[].class);
       
        String contentDisposition = String.valueOf(response.getHeaders().get("Content-Disposition")).replace("[", "").replace("]", "");
        String contentData = contentDisposition.substring(contentDisposition.indexOf("=")+1);
        
        log.info("response.contentData() : "+contentData);
        
    	String path = serverFilePath; //폴더 경로
    	File Folder = new File(path);

    	// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
    	if (!Folder.exists()) {
    		try{
    		   	Folder.mkdir(); //폴더 생성합니다.
    	    }catch(Exception e){
    	       	e.getStackTrace();
    	    }        
        }
    	
        Files.write(Paths.get(serverFilePath+contentData), response.getBody());
        
        Object object = contentData;
        		
		return object;
        
    }
	
	@GetMapping("/urlDownload/{title}")
	public ResponseEntity<?> downloadFile(@PathVariable String title, ModelMap model, HttpServletResponse res) throws Exception{
		
		log.info("title : "+title);
        Path path = Paths.get(serverFilePath+title);
        
        String contentType = Files.probeContentType(path);
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        
    	File file = new File(String.valueOf(path));
    	if( file.exists() ){
    		file.delete();
    	}
        	
		return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_TYPE, contentType)
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + title +"\"")
              .body(resource);
	}
}

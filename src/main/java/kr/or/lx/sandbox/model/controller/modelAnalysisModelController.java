package kr.or.lx.sandbox.model.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.lx.common.ApiService;
import lombok.extern.slf4j.Slf4j;


@RequestMapping("sandbox/model/modelAnalysisModel")
@Slf4j
@Controller
public class modelAnalysisModelController {
	
	@Value("${sandbox.api.url}")
    private String sandboxApiUrl;	
    
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * 분석 모델 관리 - 데이터 분석 모델
     * @return
     */
	@GetMapping("/list")
	public String modelAnalysisModel(ModelMap model) throws Exception{
		
		return "sandbox/model/modelAnalysisModel/list";
	}
	
	/**
     * 분석 모델 관리 - 데이터 분석 모델 - 등록 화면
     * @return
     */
	@GetMapping("/add")
	public String modelAnalysisModelAdd(ModelMap model) throws Exception{
		
		return "sandbox/model/modelAnalysisModel/add";
	}
	
	/**
     * 분석 모델 관리 - 데이터 전처리 모델 - 수정 화면
     * @return
     */
	@GetMapping("/update/{updateId}")
	public String modelAnalysisModelUpdate(@PathVariable String updateId, ModelMap model) throws Exception{
		
		model.addAttribute("anals_model_id", updateId);
		return "sandbox/model/modelAnalysisModel/update";
	}
	
	/**
     * 분석 모델 API
     * @return
     */		
	@ResponseBody
	@PostMapping("{apiId}")
	public Object analysisModelsMng(@RequestBody Map<String, Object> param, ModelMap model) throws Exception{
		log.info("analysisModelsMng");
		
		String url = sandboxApiUrl+param.get("url");
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}
	
	/**
	 * 분석 모델 등록
	 * @return
	 */	
	@ResponseBody
	@PostMapping("/add/{apiId}")
	public Object analysisModelsEnroll(MultipartHttpServletRequest multipartRequest, ModelMap model) throws Exception{
		log.info("analysisModelsEnroll");
		log.info("param : "+ObjectUtils.isEmpty(multipartRequest));
		
		String url = sandboxApiUrl+multipartRequest.getParameter("url");
		log.info("url : " + url);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		
		Enumeration<String> params = multipartRequest.getParameterNames();
		while(params.hasMoreElements()) {
			String name = (String)params.nextElement();
			if(!"url".equals(name)) {
				body.add(name, multipartRequest.getParameter(name));
			}
//		    System.out.println(name + " : " +multipartRequest.getParameter(name));
		}
		
		if (ObjectUtils.isEmpty(multipartRequest) == false) {
			Iterator<String> filenameIterator = multipartRequest.getFileNames();
			String name;
			while (filenameIterator.hasNext()) {
				name = filenameIterator.next();
				log.info("File name tag : " + name);
				List<MultipartFile> fileList = multipartRequest.getFiles(name);
				if(fileList.size() > 0) {
					for (MultipartFile multipartFile : fileList) {
						body.add(name, multipartFile.getResource());
					}
				}
				
			}
		}		
		
		ResponseEntity<?> responseEntity = apiService.post(url, body);
		Object object = responseEntity.getBody();
		return object;
	}
	
	
}

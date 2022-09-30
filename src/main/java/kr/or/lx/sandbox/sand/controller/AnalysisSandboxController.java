package kr.or.lx.sandbox.sand.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.lx.common.ApiService;
import lombok.extern.slf4j.Slf4j;


@RequestMapping("sandbox/sand/analysisSandbox")
@Slf4j
@Controller
public class AnalysisSandboxController {
	
    @Value("${sandbox.api.url}")
    private String sandboxApiUrl;	
    
	@Value("${login.userId}")
    private String nonUserId;
	
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * 분석 샌드박스 관리
     * @return
     */
	@GetMapping("/list")
	public String analysisSandbox(ModelMap model) throws Exception{
		
		return "sandbox/sand/analysisSandbox/list";
	}
	
	/**
     * 분석 샌드박스 API
     * @return
     */	
	@ResponseBody
	@PostMapping("{apiId}")
	public Object analysisSandboxs(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == "noLogin") {
			userId = nonUserId;
		}
		
		String url = sandboxApiUrl+param.get("url");
		param.put("user_id", String.valueOf(userId));		
		
		ResponseEntity<?> responseEntity = apiService.post(url, param);
		Object object = responseEntity.getBody();
		
		return object;
	}	
	
	/**
	 * 이미지 데이터셋 파일 업로드 첨부 API
	 * @return
	 */	
	@ResponseBody
	@PostMapping("/file/{apiId}")
	public Object analysisSandboxsFileUpload(MultipartHttpServletRequest multipartRequest, HttpSession session, ModelMap model) throws Exception{
		log.info("param : "+ObjectUtils.isEmpty(multipartRequest));
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == "noLogin") {
			userId = nonUserId;
		}
		
		String url = sandboxApiUrl+multipartRequest.getParameter("url");
		String menu_id = multipartRequest.getParameter("menu_id");
		String image_dataset_id = multipartRequest.getParameter("image_dataset_id");
		String anals_data_sect_cd = multipartRequest.getParameter("anals_data_sect_cd");
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("creator_id", userId);
		body.add("modifier_id", userId);
		body.add("user_id", userId);
		body.add("menu_id", menu_id);
		body.add("image_dataset_id", image_dataset_id);
		body.add("anals_data_sect_cd", anals_data_sect_cd);
		
		if (ObjectUtils.isEmpty(multipartRequest) == false) {
			Iterator<String> filenameIterator = multipartRequest.getFileNames();
			String name;
			while (filenameIterator.hasNext()) {
				name = filenameIterator.next();
				List<MultipartFile> fileList = multipartRequest.getFiles(name);
				for (MultipartFile multipartFile : fileList) {
					body.add(name, multipartFile.getResource());
				}
			}
		}		
		
		ResponseEntity<?> responseEntity = apiService.post(url, body);
		Object object = responseEntity.getBody();
		
		return object;
	}		
	/**
	 * 샌드박스 설정
	 * @return
	 */
	@GetMapping("/step1")
	public String analysisSandboxStep1(ModelMap model) throws Exception{
		
		return "sandbox/sand/analysisSandbox/step1";
	}
	
	/**
	 * 시나리오 선택
	 * @return
	 */
	@GetMapping("/step2/{sandbox_id}/{anals_use_prpos_cd}/{anals_use_prpos_cd_nm}")
	public String analysisSandboxStepSet2(@PathVariable String sandbox_id, @PathVariable String anals_use_prpos_cd, @PathVariable String anals_use_prpos_cd_nm, ModelMap model) throws Exception{
		
		model.put("sandbox_id", sandbox_id);		
		model.put("anals_use_prpos_cd", anals_use_prpos_cd);		
		model.put("anals_use_prpos_cd_nm", anals_use_prpos_cd_nm);		
		return "sandbox/sand/analysisSandbox/step2";
	}
	
	/**
	 * 분석 방식 선택
	 * @return
	 */
	@GetMapping("/step3/{sandbox_id}/{anals_use_prpos_cd}/{cd}")
	public String analysisSandboxStep3(@PathVariable String sandbox_id, @PathVariable String anals_use_prpos_cd, @PathVariable String cd, ModelMap model) throws Exception{
		
		model.put("sandbox_id", sandbox_id);		
		model.put("anals_use_prpos_cd", anals_use_prpos_cd);		
		model.put("cd", cd);			
		
		String pageType = "object";
		if("B13001".equals(anals_use_prpos_cd)) {
			pageType = "time";
		}
		
		return "sandbox/sand/analysisSandbox/step3_"+pageType;
	}
	
	/**
	 * 샌드박스 수정
	 * @return
	 */
	@GetMapping("/update_step1/{sandbox_id}")
	public String analysisSandboxUpdateStep1(@PathVariable String sandbox_id, ModelMap model) throws Exception{
		
		model.put("sandbox_id", sandbox_id);
		return "sandbox/sand/analysisSandbox/update_step1";
	}
	
	/**
	 * 시나리오 수정
	 * @return
	 */
	@GetMapping("/update_step2/{sandbox_id}/{anals_use_prpos_cd}")
	public String analysisSandboxStepUpdateSet2(@PathVariable String sandbox_id, @PathVariable String anals_use_prpos_cd, ModelMap model) throws Exception{
		
		model.put("sandbox_id", sandbox_id);		
		model.put("anals_use_prpos_cd", anals_use_prpos_cd);		
		return "sandbox/sand/analysisSandbox/update_step2";
	}
	
	/**
	 * 분석 방식 수정
	 * @return
	 */
	@GetMapping("/update_step3/{sandbox_id}/{anals_use_prpos_cd}/{cd}")
	public String analysisSandboxUpdateStep3(@PathVariable String sandbox_id, @PathVariable String anals_use_prpos_cd, @PathVariable String cd, ModelMap model) throws Exception{
		
		model.put("sandbox_id", sandbox_id);		
		model.put("anals_use_prpos_cd", anals_use_prpos_cd);		
		model.put("cd", cd);		
		
		String pageType = "object";
		if("B13001".equals(anals_use_prpos_cd)) {
			pageType = "time";
		}
		
		return "sandbox/sand/analysisSandbox/update_step3_"+pageType;
	}
	
}

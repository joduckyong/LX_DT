package kr.or.lx.sandbox.scenario.controller;

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


@RequestMapping("sandbox/scenario/analysisScenario")
@Slf4j
@Controller
public class AnalysisScenarioController {
	
	@Value("${sandbox.api.url}")
    private String sandboxApiUrl;	
    
	@Value("${login.userId}")
    private String nonUserId;
	
	@Autowired
	private ApiService<?> apiService;
	
	/**
     * 분석 시나리오 관리
     * @return
     */
	@GetMapping("/list")
	public String analysisScenario(ModelMap model) throws Exception{
		
		return "sandbox/scenario/analysisScenario/list";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 등록 - 시나리오 작성
     * @return
     */
	@GetMapping(value={"/step1", "/step1/{scenarioId}"})
	public String analysisScenarioStep1(@PathVariable(required = false) String scenarioId, ModelMap model) throws Exception{
		
		model.addAttribute("scenario_id", scenarioId != null ? scenarioId : "");
		return "sandbox/scenario/analysisScenario/step1";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 등록 - 분석 모델 설정
     * @return
     */
	@GetMapping("/step2/{analsDetailSettingCd}/{analsUsePrposCd}/{scenarioId}")
	public String analysisScenarioStep2(@PathVariable String analsUsePrposCd, @PathVariable String scenarioId, @PathVariable String analsDetailSettingCd, ModelMap model) throws Exception{
		
		model.addAttribute("anals_use_prpos_cd", analsUsePrposCd);
		model.addAttribute("scenario_id", scenarioId);
		model.addAttribute("anals_detail_setting_cd", analsDetailSettingCd);
		return "sandbox/scenario/analysisScenario/step2";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 등록 - 전처리 모델 설정
     * @return
     */
	@GetMapping("/step3/{analsDetailSettingCd}/{analsUsePrposCd}/{scenarioId}")
	public String analysisScenarioStep3(@PathVariable String analsUsePrposCd, @PathVariable String scenarioId, @PathVariable String analsDetailSettingCd, ModelMap model) throws Exception{
		
		model.addAttribute("anals_use_prpos_cd", analsUsePrposCd);
		model.addAttribute("scenario_id", scenarioId);
		model.addAttribute("anals_detail_setting_cd", analsDetailSettingCd);
		return "sandbox/scenario/analysisScenario/step3";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 등록 - 분석 대상 설정
     * @return
     */
	@GetMapping("/step4/{analsDetailSettingCd}/{analsUsePrposCd}/{scenarioId}")
	public String analysisScenarioStep4(@PathVariable String analsUsePrposCd, @PathVariable String scenarioId, @PathVariable String analsDetailSettingCd, ModelMap model) throws Exception{
		
		String returUrl = "";
		if("B13001".equals(analsUsePrposCd)) {	//시계열 분석
			returUrl = "sandbox/scenario/analysisScenario/step4_time";
		}else if("B13002".equals(analsUsePrposCd)){	//객체 감지
			returUrl = "sandbox/scenario/analysisScenario/step4_object";
		}
		
		model.addAttribute("anals_use_prpos_cd", analsUsePrposCd);
		model.addAttribute("scenario_id", scenarioId);
		model.addAttribute("anals_detail_setting_cd", analsDetailSettingCd);
		return returUrl;
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 수정 - 시나리오 작성
     * @return
     */
	@GetMapping("/update/step1/{scenarioId}")
	public String analysisScenarioUpdateStep1(@PathVariable String scenarioId, ModelMap model) throws Exception{
		
		model.addAttribute("scenario_id", scenarioId);
		return "sandbox/scenario/analysisScenario/update_step1";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 수정 - 분석 모델 설정
     * @return
     */
	@GetMapping("/update/step2/{analsDetailSettingCd}/{analsUsePrposCd}/{scenarioId}")
	public String analysisScenarioUpdateStep2(@PathVariable String analsUsePrposCd, @PathVariable String scenarioId, @PathVariable String analsDetailSettingCd, ModelMap model) throws Exception{
		
		model.addAttribute("anals_use_prpos_cd", analsUsePrposCd);
		model.addAttribute("scenario_id", scenarioId);
		model.addAttribute("anals_detail_setting_cd", analsDetailSettingCd);
		return "sandbox/scenario/analysisScenario/update_step2";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 수정 - 전처리 모델 설정
     * @return
     */
	@GetMapping("/update/step3/{analsDetailSettingCd}/{analsUsePrposCd}/{scenarioId}")
	public String analysisScenarioUpdateStep3(@PathVariable String analsUsePrposCd, @PathVariable String scenarioId, @PathVariable String analsDetailSettingCd, ModelMap model) throws Exception{
		
		model.addAttribute("anals_use_prpos_cd", analsUsePrposCd);
		model.addAttribute("scenario_id", scenarioId);
		model.addAttribute("anals_detail_setting_cd", analsDetailSettingCd);
		return "sandbox/scenario/analysisScenario/update_step3";
	}
	
	/**
     * 분석 시나리오 관리 - 시나리오 수정 - 분석 대상 설정
     * @return
     */
	@GetMapping("/update/step4/{analsDetailSettingCd}/{analsUsePrposCd}/{scenarioId}")
	public String analysisScenarioUpdateStep4(@PathVariable String analsUsePrposCd, @PathVariable String scenarioId, @PathVariable String analsDetailSettingCd, ModelMap model) throws Exception{
		
		String returUrl = "";
		if("B13001".equals(analsUsePrposCd)) {	//시계열 분석
			returUrl = "sandbox/scenario/analysisScenario/update_step4_time";
		}else if("B13002".equals(analsUsePrposCd)){	//객체 감지
			returUrl = "sandbox/scenario/analysisScenario/update_step4_object";
		}
		
		model.addAttribute("anals_use_prpos_cd", analsUsePrposCd);
		model.addAttribute("scenario_id", scenarioId);
		model.addAttribute("anals_detail_setting_cd", analsDetailSettingCd);
		return returUrl;
	}
	
	/**
     * 분석 시나리오 관리 - 분석 시나리오 API
     * @return
     */		
	@ResponseBody
	@PostMapping("{apiId}")
	public Object analysisScenarioMng(@RequestBody Map<String, Object> param, HttpSession session, ModelMap model) throws Exception{
		
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
	
	
	
}

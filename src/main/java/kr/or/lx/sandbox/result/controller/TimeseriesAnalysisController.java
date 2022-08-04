package kr.or.lx.sandbox.result.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@RequestMapping("sandbox/result/timeseriesAnalysis")
@Slf4j
@Controller
public class TimeseriesAnalysisController {
	
	/**
     * 데이터 분석 결과 - 시계열분석 - 데이터 로딩
     * @return
     */
	@GetMapping("/step1")
	public String timeseriesAnalysisStep1(ModelMap model) throws Exception{
		
		return "sandbox/result/timeseriesAnalysis/step1";
	}
	
	/**
     * 데이터 분석 결과 - 시계열분석 - 데이터 전처리
     * @return
     */
	@GetMapping("/step2")
	public String timeseriesAnalysisStep2(ModelMap model) throws Exception{
		
		return "sandbox/result/timeseriesAnalysis/step2";
	}
	
	/**
     * 데이터 분석 결과 - 시계열분석 - 결과 비교
     * @return
     */
	@GetMapping("/step3")
	public String timeseriesAnalysisStep3(ModelMap model) throws Exception{
		
		return "sandbox/result/timeseriesAnalysis/step3";
	}
	
	/**
     * 데이터 분석 결과 - 시계열분석 - 데이터 재학습 결과
     * @return
     */
	@GetMapping("/step4")
	public String timeseriesAnalysisStep4(ModelMap model) throws Exception{
		
		return "sandbox/result/timeseriesAnalysis/step4";
	}
	
	/**
	 * 데이터 분석 결과 - 시계열분석 - 데이터 분석 결과
	 * @return
	 */
	@GetMapping("/step5")
	public String timeseriesAnalysisStep5(ModelMap model) throws Exception{
		
		return "sandbox/result/timeseriesAnalysis/step5";
	}
	
}

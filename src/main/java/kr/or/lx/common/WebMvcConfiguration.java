package kr.or.lx.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Autowired
	LoggerInterceptor loggerInterceptor;
	
	@Value("${resource.path}")
	private String resourcePath;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Value("${cross.domain.link1}")
	private String crossDomainLink1;
	
	@Value("${cross.domain.link2}")
	private String crossDomainLink2;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
      // 모든 uri에 대해 http://220.120.28.20:8087, http://220.120.28.20:8811 도메인은 접근을 허용한다.
        registry.addMapping("/**")
                .allowedOrigins(crossDomainLink1, crossDomainLink2);
    }
    
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(loggerInterceptor)
		.addPathPatterns("/server/**")
		.addPathPatterns("/sandbox/**")
		.addPathPatterns("/dms/**");
	}
	
	@Override
	public void addResourceHandlers (ResourceHandlerRegistry registry) {
		registry.addResourceHandler(uploadPath)
				.addResourceLocations(resourcePath);
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(500 * 1024 * 1024);
		return commonsMultipartResolver;
	}		
}

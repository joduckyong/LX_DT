package kr.or.lx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class})
public class WebApplication extends SpringBootServletInitializer { 
	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { 
		return application.sources(WebApplication.class); 
	} 
	
	public static void main(String[] args) { 
			SpringApplication.run(WebApplication.class, args); 
		} 
	}


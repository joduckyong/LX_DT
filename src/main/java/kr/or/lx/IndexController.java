package kr.or.lx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@Value("${init.url}")
    private String initUrl;
	
    @GetMapping("/")
    public String index() {
        
        return "redirect:"+initUrl;
    }
       
}

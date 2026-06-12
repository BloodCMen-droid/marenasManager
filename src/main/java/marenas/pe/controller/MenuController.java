package marenas.pe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
	
	@GetMapping("/")
    public String home() {
        return "redirect:/index";
    }
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

}

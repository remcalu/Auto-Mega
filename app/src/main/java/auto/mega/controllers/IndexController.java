package auto.mega.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "There are several endpoints available...\n\n " + 
		"http://localhost:8080/autotrader-data";
	}

}
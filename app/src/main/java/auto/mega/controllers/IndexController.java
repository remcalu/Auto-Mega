package auto.mega.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "There are several endpoints available... </br></br>" + 
    "GET:  /api/vehicles </br>" + 
    "GET:  /api/vehicles/count </br>" +
    "GET:  /api/vehicles/clear </br>" +
    "POST: /api/vehicles/refetch";
	}

}
package com.cinyema.app.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/cinyema","/"})
public class MainControlador {
	
	@GetMapping()
	public String index() {			
		return "index.html";
	}
}

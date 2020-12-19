package mnascimento.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import Info.Info;

@RestController
public class InfoController {

	@GetMapping("/start")
	public Info Start() {
		return new Info((long) 1, "Jhon", "12345678901");
	}
}

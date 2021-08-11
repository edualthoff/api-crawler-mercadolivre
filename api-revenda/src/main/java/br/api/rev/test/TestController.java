package br.api.rev.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "Deu Certo";
	}

}

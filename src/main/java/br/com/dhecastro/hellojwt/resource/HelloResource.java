package br.com.dhecastro.hellojwt.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dhecastro.hellojwt.model.Response;

@RestController
@RequestMapping("/hello")
public class HelloResource {

	@GetMapping("/1")
	public Response testehello() {
		Response res = new Response();
		res.setResposta("Testando");
		return res;
	}
}

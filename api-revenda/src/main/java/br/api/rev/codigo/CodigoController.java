package br.api.rev.codigo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/codigo")
public class CodigoController {

	@Autowired
	private CodigoCadastroService codigoCadastroService;
	
	
	@GetMapping("/verificar")
	public boolean existCodigo(@RequestParam(value = "codigo", required = true) String codigo) {
		System.out.println("teste");
		return this.codigoCadastroService.validCodigoCadastro(codigo);
	}
	
	public List<String> listModulos(@PathVariable(value = "codigo", required = true) String codigo){
		return this.codigoCadastroService.buscarCodigoModulosNomes(codigo);
	}
}

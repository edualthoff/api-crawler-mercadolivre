package br.api.rev.pessoa;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.api.rev.session.SessionRevendaServiceImp;


@RestController
@RequestMapping(path = "/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping("/principal")
	public Map<String, Object> createAccountPrincipal(@RequestParam(value = "codigo") String codigo, @RequestBody Pessoa pessoa) {
		System.out.println(" teste "+codigo+" "+pessoa.getDataNascimento()+" "+pessoa.getNome()+" "+pessoa.getEmail());
		Pessoa p = this.pessoaService.createPessoaPrincipal(codigo, pessoa);
		String session = new SessionRevendaServiceImp(p.getTenantId(), p.getId()).gerarSession();
		Map<String, Object> mapp = new HashMap<>();
		mapp.put("pessoa", p);
		mapp.put("session", session);
		return mapp;
	}
}

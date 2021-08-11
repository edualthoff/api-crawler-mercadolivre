package br.api.rev.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.rev.codigo.CodigoCadastro;
import br.api.rev.codigo.CodigoCadastroService;
import br.api.rev.modulo.ModuloAplicacao;
import br.api.rev.session.TenantService;



@Service
public class PessoaService {

	@Autowired private PessoaDAO pessoaDAO;
	@Autowired private CodigoCadastroService codigoService;

	public Pessoa createPessoa(Pessoa pessoa) {
		return this.pessoaDAO.save(pessoa);
	}
	
	@Transactional
	public Pessoa createPessoaPrincipal(String codigo, Pessoa pessoa) {
		CodigoCadastro codigoCadastro = this.codigoService.buscarCodigo(codigo);
		List<ModuloAplicacao> newsM = new ArrayList<>();
		newsM.addAll(codigoCadastro.getModuloAplicacao());
		pessoa.setTenantId(TenantService.buildIdString(pessoaDAO));
		pessoa.setModuloAplicacao(newsM);
		return pessoaDAO.save(pessoa);
	}
}

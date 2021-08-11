package br.api.rev.codigo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.rev.modulo.ModuloAplicacao;



@Service
public class CodigoCadastroService {

	@Autowired 
	private CodigoCadastroDAO codigoCadastroDAO;
	
	/**
	 * Retorna se o codigo de cadastro esta ativo ou inativo - true ou false
	 * @param codigo_gerado
	 * @return
	 */
	public boolean validCodigoCadastro(String codigoGerado) {
		return codigoCadastroDAO.existsByCodigoGerado(codigoGerado);
	}
	
	/**
	 * Retorna uma instancia de CodigoCadastro, buscando pelo codigo que foi gerado
	 * @param codigoGerado
	 * @return
	 */
	public CodigoCadastro buscarCodigo(String codigoGerado) {
		return codigoCadastroDAO.findByCodigoGerado(codigoGerado);
	}
	
	public List<String> buscarCodigoModulosNomes(String codigoGerado) {
		List<ModuloAplicacao> modulos = codigoCadastroDAO.findByCodigoGerado(codigoGerado).getModuloAplicacao();
		List<String> newsLista = new ArrayList<String>();
		modulos.forEach((x) -> {
			newsLista.add(x.getNome());
		});
		return newsLista;
	}
}

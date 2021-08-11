package br.ml.api.crawler.busca;

import java.util.Iterator;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/links-mercadolivre")
public class CrawlerBuscaUrlController {

	private static final Logger log = LoggerFactory.getLogger(CrawlerBuscaUrlController.class);

	@Autowired
	private CrawlerBuscaUrlService searchPService;

	/**
	 * Adicionar um novo link de busca de produto
	 * @param searchProduto
	 * @return
	 */
	@PostMapping()
	public CrawlerBuscaUrl adicionar(@RequestBody @Valid CrawlerBuscaUrl searchProduto){
		log.debug("value: "+searchProduto.getRangeFinal()+" "+searchProduto.getRangeInicial());
		return searchPService.adicionarLink(searchProduto); 
	}
	/**
	 * Atualizar um Link de busca
	 * @param idLinks
	 * @return
	 */
	@PutMapping("/{idLinks}")
	public CrawlerBuscaUrl atualizarProduto(@PathVariable(value = "idLinks") Long idLinks, @RequestBody @Valid CrawlerBuscaUrl searchProduto){
		log.debug("itens "+ searchProduto.toString()+" id: "+idLinks);
		return searchPService.atualizar(idLinks, searchProduto); 
	}
	/**
	 * Buscar Todos
	 * @return
	 */
	@GetMapping("/")
	public Iterator<CrawlerBuscaUrl> buscarTodos() {
		log.debug("buscar todos");
		return searchPService.buscarTodos();
	}
	/**
	 * Buscar Todos
	 * @return
	 */
	@GetMapping("/status/{status}/errorlink/{errorlink}")
	public Iterator<CrawlerBuscaUrl> buscarStatusAndErrorLinkTodos(@PathVariable(value = "status") boolean status, @PathVariable(value = "errorlink") boolean errorlink) {
		log.debug("buscar todos "+ status+" "+errorlink);
		return searchPService.buscarStatusError(status, errorlink);
	}
	/**
	 * Busar passando o ID como parametro
	 * @param idSearch
	 * @return
	 */
	@GetMapping("/{idSearch}")
	public CrawlerBuscaUrl buscarPorID(@PathVariable(value = "idSearch") Long idSearch) {
		log.debug("buscar id "+idSearch);
		return searchPService.buscarPorId(idSearch);
	}
	/**
	 * Excluir
	 * @param idSearch
	 */
	@DeleteMapping("/{idSearch}")
	public void excluirPorId(@PathVariable(value = "idSearch") @Valid Long idSearch) {
		searchPService.excluir(idSearch);		
	}
	
	@GetMapping(path = "/todos")
	public Page<CrawlerBuscaUrl> buscarAllPage (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size){
		
		log.debug("pag "+page+" "+size);
	    return this.searchPService.buscarAllPagination(page, size);
	 
	}
	
}

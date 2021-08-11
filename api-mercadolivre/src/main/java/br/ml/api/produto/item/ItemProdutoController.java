package br.ml.api.produto.item;

import java.util.Iterator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/itens")
public class ItemProdutoController {

	@Autowired
	private ItemProdutoService itensService;
	
	@PostMapping()
	public ItemProduto adicionarProduto(@RequestBody @Valid ItemProduto itemProduto){
		System.out.println("itens "+ itemProduto.toString());
		return itensService.adicionar(itemProduto); 
	}
	@PutMapping("/{idItens}")
	public ItemProduto atualizarProduto(@PathVariable(value = "idItens") Long idItens, @RequestBody @Valid ItemProduto itemProduto){
		System.out.println("itens "+ itemProduto.toString()+" id: "+idItens);
		return itensService.atualizar(idItens, itemProduto); 
	}
	
	@GetMapping("/categorias/{idCategoria}")
	public Iterator<ItemProduto> buscarTodosPorCategoriaId(@PathVariable(value = "idCategoria") Long idCategoria){
		return itensService.buscarAllPorCategoria(idCategoria);
	}
	
	@GetMapping("/marcas/{idMarca}")
	public Iterator<ItemProduto> buscarTodosPorMarcaId(@PathVariable(value = "idMarca") Long idMarca){
		return itensService.buscarAllPorMarca(idMarca);
	}
	
	@GetMapping()
	public Iterator<ItemProduto> buscarTodos(@RequestParam(value = "idItens") String idMarca){
		return itensService.buscarAll();
	}
	
	@GetMapping("/{idItens}")
	public ItemProduto buscarPorId(@PathVariable(value = "idItens") Long idItens){
		return itensService.buscarItensPorId(idItens);
	}
	
	@GetMapping(path = "/modelo")
	public Page<ItemProduto> buscarModeloAllPageAutoComplete (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "modelo", required = true) String modelo){
		
		System.out.println("pag "+page+" "+size+" modelo: "+modelo);
	    return this.itensService.buscarModeloAllPaginationAutoComplete(page, size, modelo);
	}
	
	@GetMapping(path = "/todos")
	public Page<ItemProduto> buscarAllPage (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size){
		
		System.out.println("pag "+page+" "+size);
	    return this.itensService.buscarAllPagination(page, size);
	 
	}
	
	@DeleteMapping("/{idItem}")
	public void deletarUmItemaId(@PathVariable(value = "idItem") Long idItem) {
		this.itensService.excluir(idItem);
	}
}

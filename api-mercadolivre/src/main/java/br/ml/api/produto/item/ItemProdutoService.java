package br.ml.api.produto.item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edx.exception.config.ApiMessageSource;
import br.edx.exception.type.ApiNotFoundException;
import br.ml.api.config.tenant.IndexTenantDynamic;


@Service
public class ItemProdutoService implements Serializable {
	private static final long serialVersionUID = -1228598954864611754L;

	@Autowired
	private ItemProdutoDAO itempDAO;
	//@Autowired
	//private ElasticsearchIdentifierGenerator elastic;
	@Autowired
	private IndexTenantDynamic index;

	
	/**
	 * 
	 * @param itemp
	 * @return
	 * @throws ApiNotFoundException
	 
	public ItemProduto adicionarOld(ItemProduto itemProduto) throws ApiNotFoundException, ApiNotFoundException {

		try {
			if (itempDAO.findByModelo(itemProduto.getModelo()).isEmpty()) {
				//itemProduto.setId(elastic.identifierId(ItemProduto.class));
				itemProduto.setTenantId(index.getTenantId());
				return this.itempDAO.save(itemProduto);
			}
			throw new ApiNotFoundException(ApiMessageSource.toMessageSetObject("objeto.add.error", "modelo"));
		} catch (NoSuchIndexException e) {
			//itemProduto.setId(elastic.identifierId(ItemProduto.class));
			itemProduto.setTenantId(index.getTenantId());
			return this.itempDAO.save(itemProduto);
		}
	}*/
	public ItemProduto adicionarOld(ItemProduto itemProduto) throws ApiNotFoundException, ApiNotFoundException {
			if (itempDAO.findByModelo(itemProduto.getModelo()).isEmpty()) {
				//itemProduto.setId(elastic.identifierId(ItemProduto.class));
				itemProduto.setTenantId(index.getTenantId());
				return this.itempDAO.save(itemProduto);
			}
			throw new ApiNotFoundException(ApiMessageSource.toMessageSetObject("objeto.add.error", "modelo"));

	}
	public ItemProduto adicionar(ItemProduto itemProduto) throws ApiNotFoundException, ApiNotFoundException {
		//itemProduto.setId(elastic.identifierId(ItemProduto.class));
		itemProduto.setTenantId(index.getTenantId());
		return this.itempDAO.save(itemProduto);
	}

	
	public ItemProduto atualizar(Long idItens, ItemProduto itemProduto) {
		System.out.println("update item service 2" + idItens + " " + itemProduto.getId());
		if (itempDAO.existsById(idItens) && idItens.equals(itemProduto.getId())) {
			System.out.println("update item service");
			itemProduto.setTenantId(index.getTenantId());
			return this.itempDAO.save(itemProduto);
		}
		throw new ApiNotFoundException(ApiMessageSource.toMessageSetObject("objeto.add.error", "nome"));
	}

	public Iterator<ItemProduto> buscarAll() {
		return itempDAO.findAll().iterator();
	}

	public Iterator<ItemProduto> buscarAllPorCategoria(Long idCategodia) {
		return itempDAO.findAllByIdCategoriaIdIn(Arrays.asList(idCategodia)).iterator();
	}

	public Iterator<ItemProduto> buscarAllPorMarca(Long idMarca) {
		return itempDAO.findAllByIdMarcaIdIn(Arrays.asList(idMarca)).iterator();
	}

	public ItemProduto buscarItensPorId(Long idItens) {
		return itempDAO.findById(idItens).get();
	}

	/**
	 * Buscar todos os ITENS - Paginacao
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<ItemProduto> buscarAllPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("modelo"));
		Page<ItemProduto> pageResult = itempDAO.findAll(pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	/**
	 * Buscar itens por modelo - Paginacao
	 * 
	 * @param pageable
	 * @param modelo
	 * @return
	 */
	public Page<ItemProduto> buscarModeloAllPaginationAutoComplete(int page, int size, String keywords) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<ItemProduto> pageResult = itempDAO.findByModeloContainingIgnoreCase(keywords, pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	public void excluir(Long idItem) {
		itempDAO.deleteById(idItem);
	}

	public ItemProduto buscarPorNome(String modelo) throws ApiNotFoundException {
		return itempDAO.findByModelo(modelo).orElseThrow(() -> (new ApiNotFoundException(
				ApiMessageSource.toMessageSetObject("objeto.error.null.msg.object", "Produ, Item"))));
	}
}

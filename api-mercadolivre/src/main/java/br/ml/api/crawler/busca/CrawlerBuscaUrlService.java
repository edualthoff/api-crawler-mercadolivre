package br.ml.api.crawler.busca;

import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edx.exception.config.ApiMessageSource;
import br.edx.exception.type.ApiBadRequestException;
import br.edx.exception.type.ApiNotFoundException;
import br.ml.api.config.tenant.IndexTenantDynamic;
import br.ml.api.crawler.busca.mercadolivre.BuscasMercadoLivre;


@Service
public class CrawlerBuscaUrlService  {

	@Autowired 
	private CrawlerBuscaUrlDAO buscasMercadoLivreDAO;
	@Autowired 
	private IndexTenantDynamic index;

	/**
	 * Adicionar uma busca de produto automatica
	 * 
	 * @param buscasMercadoLivre
	 * @return
	 * @throws ApiNotFoundException
	 
	public CrawlerBuscaUrl adicionarLink(CrawlerBuscaUrl crawlerBuscaUrl) throws ApiNotFoundException, NoSuchIndexException {
		BuscasMercadoLivre buscarItemMercadoLivre = new BuscasMercadoLivre(crawlerBuscaUrl);
		//buscasMercadoLivre.setId(elastic.identifierId(BuscasMercadoLivre.class));
		crawlerBuscaUrl.setTenantId(index.getTenantId());
		crawlerBuscaUrl.getSchedulingTime().setNextDate(crawlerBuscaUrl.getSchedulingTime().nextDateUpdateOrCreate());
		crawlerBuscaUrl.setSiteBase(buscarItemMercadoLivre.getSiteBase());
		try {
			return buscasMercadoLivreDAO.save(buscarItemMercadoLivre.validarCampos().build());
		} catch (NoSuchIndexException e) {
			return this.buscasMercadoLivreDAO.save(buscarItemMercadoLivre.validarCampos().build());
		}
		// throw new ApiNotFoundException(ApiMessageSource.toMessageSetObject("objeto.add.error", "links Mercado Livre""));
	}*/
	public CrawlerBuscaUrl adicionarLink(CrawlerBuscaUrl crawlerBuscaUrl) throws ApiNotFoundException {
		BuscasMercadoLivre buscarItemMercadoLivre = new BuscasMercadoLivre(crawlerBuscaUrl);
		//buscasMercadoLivre.setId(elastic.identifierId(BuscasMercadoLivre.class));
		crawlerBuscaUrl.setTenantId(index.getTenantId());
		crawlerBuscaUrl.setNextDate(crawlerBuscaUrl.nextSchedulingStart());
		crawlerBuscaUrl.setSiteBase(buscarItemMercadoLivre.getSiteBase());
		return buscasMercadoLivreDAO.save(buscarItemMercadoLivre.validarCampos().build());

		// throw new ApiNotFoundException(ApiMessageSource.toMessageSetObject("objeto.add.error", "links Mercado Livre""));
	}

	/**
	 * Atualizar todos simultaneno, passando uma lista de Buscas Mercado Livre
	 * @param listBuscasMercadoLivre
	 */
	public void atualizarTodos(List<CrawlerBuscaUrl> listBuscasMercadoLivre) {
		this.buscasMercadoLivreDAO.saveAll(listBuscasMercadoLivre);
		this.buscasMercadoLivreDAO.flush();
	}
	
	/**
	 * Atualizar Busca de produto automatica
	 * 
	 * @param buscasMercadoLivre
	 * @param idLinkML
	 * @return
	 */
	public CrawlerBuscaUrl atualizar(Long idLinkML, CrawlerBuscaUrl crawlerBuscaUrl) {
		CrawlerBuscaUrl sp = this.buscarPorId(idLinkML);
		if (crawlerBuscaUrl.getId().equals(sp.getId())) {
			BuscasMercadoLivre buscarItemMercadoLivre = new BuscasMercadoLivre(crawlerBuscaUrl);
			//searchProduto.setId(elastic.identifierId(BuscasMercadoLivre.class));
			//searchProduto.setTenantID(index.getTenantID());
			crawlerBuscaUrl.setId(sp.getId());
			crawlerBuscaUrl.setTenantId(sp.getTenantId());
			//buscasMercadoLivre.getSchedulingTime().setNextDate(buscasMercadoLivre.getSchedulingTime().nextDateUpdateOrCreate());
			crawlerBuscaUrl.setNextDate(crawlerBuscaUrl.nextSchedulingStart());
			return this.buscasMercadoLivreDAO.save(buscarItemMercadoLivre.validarCampos().build());
		}
		throw new ApiBadRequestException(ApiMessageSource.toMessageSetObject("objeto.update.error", "nome"));
	}

	/**
	 * Buscar todas as Categorias - Paginacao
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<CrawlerBuscaUrl> buscarAllPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<CrawlerBuscaUrl> pageResult = buscasMercadoLivreDAO.findAll(pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	/**
	 * Buscar todas as buscas automaticas de produtos cadastrado
	 * 
	 * @return
	 */
	public Iterator<CrawlerBuscaUrl> buscarTodos() {
		return buscasMercadoLivreDAO.findAll().iterator();
	}
	
	/**
	 * Buscas todos por status @param boolean true ou false
	 * @param status
	 * @return
	 */
	public Iterator<CrawlerBuscaUrl> buscarStatusTodos(boolean status) {
		return buscasMercadoLivreDAO.findByStatus(status).iterator();
	}
	/**
	 * Buscas todos por status @param boolean true ou false
	 * @param status
	 * @return
	 */
	public List<CrawlerBuscaUrl> buscarStatusAndErrorLinkTodos(boolean status, boolean errorLink) {
		return buscasMercadoLivreDAO.findByStatusAndErrorLink(status, errorLink);
	}
	
	/**
	 * 
	 * @param status
	 * @param errorLink
	 * @param offsetDateTime
	 * @return List<BuscasMercadoLivre>
	 
	public Page<BuscasMercadoLivre> buscarTodosParamStatusAndErrorLinkAndDataMenorOuIgual(boolean status, boolean errorLink, OffsetDateTime offsetDateTime, int page ) {
		QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("status", true)).must(QueryBuilders.matchQuery("errorLink", false))
				.filter(QueryBuilders.nestedQuery("schedulingTime", QueryBuilders.rangeQuery("schedulingTime.nextDate").lte(offsetDateTime), ScoreMode.Max));
		
		PageRequest pageRequest = PageRequest.of(page, 30);
		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(qb).withPageable(PageRequest.of(page, 30)).build();
		SearchHitsIterator<BuscasMercadoLivre> stream = elkTemplate.searchForStream(nativeSearchQuery, BuscasMercadoLivre.class);
		List<BuscasMercadoLivre> bmlist = new ArrayList<>();
		while (stream.hasNext()) {
			bmlist.add(stream.next().getContent());
		}
		stream.close();
		//return new PageImpl<>(bmlist);
		return searchDao.findBySchedulingTimeNextDateLessThanEqual(status, errorLink, offsetDateTime, pageRequest);
	}*/
	
	public Page<CrawlerBuscaUrl> buscarTodosParamStatusAndErrorLinkAndDataMenorOuIgual(
			boolean status, boolean errorLink, OffsetDateTime offsetDateTime, int page ) {
		PageRequest pageRequest = PageRequest.of(page, 30);
		return buscasMercadoLivreDAO.findByStatusAndErrorLinkAndNextDateLessThanEqual(status, errorLink, offsetDateTime, pageRequest);
	}
	
	public Iterator<CrawlerBuscaUrl> buscarStatusError(boolean status, boolean errorLink) {
		return buscasMercadoLivreDAO.findByStatusAndErrorLink(status, errorLink).iterator();
	}
	
	public CrawlerBuscaUrl buscarPorId(Long idSearch) {
		return buscasMercadoLivreDAO.findById(idSearch).orElseThrow(() -> (new ApiNotFoundException(
				ApiMessageSource.toMessageSetObject("objeto.error.null.msg.object", "Link Produto"))));
	}

	/**
	 * Exluir uma uma busca de produto automatica
	 * 
	 * @param searchId
	 */
	public void excluir(Long searchId) {
		buscasMercadoLivreDAO.deleteById(searchId);
	}
}
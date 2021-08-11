package br.ml.api.crawler.item;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edx.exception.config.ApiMessageSource;
import br.edx.exception.type.ApiNotFoundException;
import br.ml.api.config.tenant.IndexTenantDynamic;
import br.ml.api.crawler.busca.CrawlerBuscaUrl;

@Service
public class CrawlerBuscaItemService {
	private static final Logger log = LoggerFactory.getLogger(CrawlerBuscaItemService.class);

	@Autowired
	private CrawlerBuscaItemDAO crawlerBuscaItemDAO;
	@Autowired
	private IndexTenantDynamic index;
	
	public CrawlerBuscaItem adicionar(CrawlerBuscaItem itemMercadoLivre) {
		//System.out.println("ListaProduto adicionar " + itemMercadoLivre.getId() + " " + itemMercadoLivre.getTenantID());
		//CrawlerBuscaItemId cr = new CrawlerBuscaItemId();
		//cr.setCrawlerItem(itemMercadoLivre.getCrawlerItem());
		if (!(crawlerBuscaItemDAO.existsByCrawlerBuscaItemPKCrawlerItemPkId(itemMercadoLivre.getCrawlerItem().getCrawlerItemPK().getId()))) {
			return crawlerBuscaItemDAO.save(itemMercadoLivre);
		}
		throw new ApiNotFoundException(
				ApiMessageSource.toMessageSetObject("objeto.add.error", "Adicionar Item - Mercadolivre -"));
	}

	public void adicionarTodos(List<CrawlerBuscaItem> itemMercadoLivre) {
		crawlerBuscaItemDAO.saveAll(itemMercadoLivre);
		crawlerBuscaItemDAO.flush();
	}

	@Transactional
	public void atualizarTodos(List<CrawlerBuscaItem> itemMercadoLivre) {
		crawlerBuscaItemDAO.saveAll(itemMercadoLivre);
		crawlerBuscaItemDAO.flush();
	}
	
	/**
	 * Update @param idUriBuscas @param tenantID
	 * @param List<ItemMercadoLivre> itemMercadoLivre
	 
	public void atualizarTodos(List<ItemMercadoLivre> itemMercadoLivre) {
		log.debug("ListaProduto adicionar size: {}", itemMercadoLivre.size());
		List<UpdateQuery> querie = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).findAndRegisterModules();
		
		for (ItemMercadoLivre item : itemMercadoLivre) {
			item.setDateModified(Instant.now());
			//System.out.println("item: "+item.getDate_created()+" "+item.getDate_modified()+" "+item.getId());
			@SuppressWarnings("unchecked")
			Map<String, Object> itensJsonMap = objectMapper.convertValue(item, Map.class);
			//System.out.println("dd: "+itensJsonMap.toString());
			UpdateQuery updateQuery = UpdateQuery.builder(item.getId()).withScriptedUpsert(true)
					.withUpsert(Document.from(itensJsonMap))
					//.withFetchSourceExcludes(Arrays.asList("date_created"))
					//.withFetchSource(true)
					.withScript("if (ctx._source != null){"
							+ "ctx._source.date_modified = params.date_modified;"
							+ "ctx._source.ativo = params.ativo;"
							+ "ctx._source.tipoAnuncio = params.tipoAnuncio;"
							+ "ctx._source.valor = params.valor;"
							+ "ctx._source.condicao = params.condicao;"
							+ "if(!ctx._source.tenantID.containsAll(params.tenantID)) {ctx._source.tenantID.addAll(params.tenantID);}"
							+ "if(!ctx._source.idUriBuscas.containsAll(params.idUriBuscas)) {ctx._source.idUriBuscas.addAll(params.idUriBuscas);}"
							+ "}")
					.withParams(itensJsonMap).build();
			querie.add(updateQuery);
		}
		elkTemplate.bulkUpdate(querie, ItemMercadoLivre.class);
	}*/

	public CrawlerBuscaItem atualizar(String idBuscaList, CrawlerBuscaItem itemMercadoLivre) {
		//CrawlerBuscaItemId cr = new CrawlerBuscaItemId();
		//cr.setTenantId(itemMercadoLivre.getTenantId());
		if (crawlerBuscaItemDAO.existsByCrawlerBuscaItemPKCrawlerItemPkId(idBuscaList)) {
			return crawlerBuscaItemDAO.save(itemMercadoLivre);
		}
		throw new ApiNotFoundException(
				ApiMessageSource.toMessageSetObject("objeto.update.error", "Atualizar Item - Mercadolivre -"));
	}

	/**
	 * Buscar lista de Itens pelo id do produto do mercadolivre
	 * 
	 * @param idSearchProduto
	 * @return
	 
	public Optional<CrawlerBuscaItem> buscarPorId(String idItem) {
		return crawlerBuscaItemDAO.findById(idItem);
	}*/

	/**
	 * Buscar Itens pelo id e pelo o status no caso se a varredura está ativa ou
	 * desativada True - ativo False - desativado
	 * 
	 * @param idSearchProduto
	 * @param status
	 * @return
	 */
	public List<CrawlerBuscaItem> buscarPorIdUriBuscasAndStatus(CrawlerBuscaUrl crawlerBuscaUrl, boolean status) {
		log.debug("---- -- ListaProduto Comp value: " + crawlerBuscaUrl + " " + status);
		return crawlerBuscaItemDAO.findByCrawlerBuscaUrlAndAtivo(crawlerBuscaUrl, status);
	}
}

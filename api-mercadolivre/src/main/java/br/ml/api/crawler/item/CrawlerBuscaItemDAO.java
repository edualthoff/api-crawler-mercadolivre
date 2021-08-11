package br.ml.api.crawler.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ml.api.crawler.busca.CrawlerBuscaUrl;

@Repository
public interface CrawlerBuscaItemDAO extends JpaRepository<CrawlerBuscaItem, CrawlerBuscaItem.CrawlerBuscaItemPK>{

	List<CrawlerBuscaItem> findByCrawlerBuscaUrlAndAtivo(CrawlerBuscaUrl idUriBuscas, boolean ativo);

	boolean existsByCrawlerBuscaItemPKCrawlerItemPkId(String id);
	

}

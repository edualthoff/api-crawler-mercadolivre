package br.ml.api.crawler.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CrawlerItemDAO extends JpaRepository<CrawlerItem, CrawlerItem.CrawlerItemPK> {

	//List<CrawlerItem> findByIdUriBuscasAndAtivo(String idUriBuscas, boolean ativo);

	
}

package br.ml.api.crawler.busca;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CrawlerBuscaUrlDAO extends JpaRepository<CrawlerBuscaUrl, Long>{

	Iterable<CrawlerBuscaUrl> findByStatus(boolean status);
	
	Iterable<CrawlerBuscaUrl> findAllByStatusAndErrorLink(boolean status, boolean errorLink);
	
	List<CrawlerBuscaUrl> findByStatusAndErrorLink(boolean status, boolean errorLink);
	
	//List<BuscasMercadoLivre> findByStatusAndErrorLinkAndSchedulingTimeNextDateLessThanEqual(boolean status, boolean errorLink, OffsetDateTime dateTime);
	
	Page<CrawlerBuscaUrl> findByStatusAndErrorLinkAndNextDateLessThanEqual(boolean status, boolean errorLink, OffsetDateTime dateTime, Pageable pageRequest);

	
	//Iterable<BuscasMercadoLivre> findByErrorLink(boolean status, boolean errorLink);
	//findByStatusAndErrorLinkAndSchedulingTimeNextDateIsLessThanEqual
}

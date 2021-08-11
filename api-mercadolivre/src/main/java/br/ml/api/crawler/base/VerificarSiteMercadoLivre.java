package br.ml.api.crawler.base;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import br.ml.api.config.ApplicationContextProvider;
import br.ml.api.crawler.FormatarLinks;
import br.ml.api.crawler.busca.CrawlerBuscaUrl;
import br.ml.api.crawler.busca.CrawlerBuscaUrlService;
import br.ml.api.crawler.mercadolivre.FormatarLinksMercadoLivre;

@Service
@Scope("prototype")
public class VerificarSiteMercadoLivre implements Serializable{
	private static final long serialVersionUID = 7307752955984583612L;
	private static final Logger log = LoggerFactory.getLogger(VerificarSiteMercadoLivre.class);

	@Autowired
	private CrawlerBuscaUrlService buscasMLService;

	@Async("baseAnaliseThread")
	public void baseAnaliseThread() {
		log.debug("Thread - baseAnaliseThread - metodod: baseAnaliseThread START");
		int page = 0;
		Page<CrawlerBuscaUrl> buscasMercadoLivre;
		List<CrawlerBuscaUrl> buscasMercadoLivreAtualizadaNextDate = new ArrayList<>();
		do {
			buscasMercadoLivre = buscasMLService.buscarTodosParamStatusAndErrorLinkAndDataMenorOuIgual(true, false, OffsetDateTime.now().plusMinutes(5L), page);
			log.debug("list size: "+buscasMercadoLivre.getSize());
			for (CrawlerBuscaUrl buscaML : buscasMercadoLivre.getContent()) {
				FormatarLinks<CrawlerBuscaUrl> gerarLink = new FormatarLinksMercadoLivre();
				String urlLink = gerarLink.gerarLinks(buscaML);
				log.debug("BuscasMercadoLivre while - Thread(baseCrawlerAnaliseThread) de link a percorrer: {} e idBusca: {}", urlLink, buscaML.getId());
				this.baseCrawlerAnaliseThread(buscaML, urlLink);
				log.debug("Hora scheduling: {}",buscaML.nextSchedulingStart());
				buscaML.setNextDate(buscaML.nextSchedulingStart());
				buscasMercadoLivreAtualizadaNextDate.add(buscaML);
				log.debug("Hora scheduling atualizada: {}",buscaML.nextSchedulingStart());
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					log.error("Thread baseCrawlerAnaliseThread - error executacao {}", e.getMessage());
				}
			}
			this.buscasMLService.atualizarTodos(buscasMercadoLivreAtualizadaNextDate);
			buscasMercadoLivreAtualizadaNextDate.clear();
			page++;
		} while (buscasMercadoLivre.hasNext());
	}

	@Async("baseCrawlerAnaliseThread")
	public void baseCrawlerAnaliseThread(CrawlerBuscaUrl buscasMercadoLivre, String urlLink) {
		try {
			ApplicationContextProvider.getApplicationContext().getBean(BaseCrawlerAnalise.class).build(buscasMercadoLivre, urlLink);
		} catch (NullPointerException e) {
			log.error("Error baseCrawlerAnaliseThread - NullPointerException - {}", e);
			Thread.currentThread().interrupt();
		}
	}
}

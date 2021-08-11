package br.ml.api.crawler.busca.mercadolivre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ml.api.crawler.busca.CrawlerBuscaUrl;
import br.ml.api.crawler.busca.ValidarCampos;



public class ValidarCamposMercadolivre implements ValidarCampos<CrawlerBuscaUrl>{
	private static final Logger log = LoggerFactory.getLogger(ValidarCamposMercadolivre.class);

	private CrawlerBuscaUrl buscasML;
	
	
	public ValidarCamposMercadolivre(CrawlerBuscaUrl crawlerBuscaUrl) {
		this.buscasML = crawlerBuscaUrl;
	}

	
	@Override
	public void linkUrl() {
		log.debug("url: {}",this.buscasML.getLinkUrl().strip().split("(\\_|\\&|\\#|\\?)")[0].replaceAll("(?i)(usado|novo|recondicionado)(\\/|)", "").replaceAll("!?[\\/]+$", "").trim()+"/");
		this.buscasML.setLinkUrl(this.buscasML.getLinkUrl().strip().split("(\\_|\\&|\\#|\\?)")[0].replaceAll("(?i)(usado|novo|recondicionado)(\\/|)", "").replaceAll("!?[\\/]+$", "").trim()+"/");
	}

	@Override
	public void rangeValue() {
		if((this.buscasML.getRangeInicial().compareTo(this.buscasML.getRangeFinal()) > 1 )) {
			this.buscasML.setRangeInicial(this.buscasML.getRangeFinal());
			this.buscasML.setRangeFinal(this.buscasML.getRangeInicial());
		}
	}

	@Override
	public CrawlerBuscaUrl build() {
		this.linkUrl();
		this.rangeValue();
		return this.buscasML;
	}

}

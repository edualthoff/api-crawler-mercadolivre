package br.ml.api.crawler.busca.mercadolivre;

import java.io.Serializable;

import br.ml.api.crawler.busca.CrawlerBuscaSiteBase;
import br.ml.api.crawler.busca.CrawlerBuscaUrl;
import br.ml.api.crawler.busca.CrawlerBuscaUrlSiteBase;
import br.ml.api.crawler.busca.ValidarCampos;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BuscasMercadoLivre implements Serializable, CrawlerBuscaUrlSiteBase{
	private static final long serialVersionUID = 8297426447697768554L;

	private CrawlerBuscaUrl crawlerBuscaUrl;
	
	public BuscasMercadoLivre(CrawlerBuscaUrl crawlerBuscaUrl) {
		super();
		this.crawlerBuscaUrl = crawlerBuscaUrl;
	}

		
	public String getSiteBase() {
		return CrawlerBuscaSiteBase.MERCADOLIVRE.getSiteBase();
	}


	@Override
	public ValidarCampos<CrawlerBuscaUrl> validarCampos() {
		return new ValidarCamposMercadolivre(crawlerBuscaUrl);
	}


}

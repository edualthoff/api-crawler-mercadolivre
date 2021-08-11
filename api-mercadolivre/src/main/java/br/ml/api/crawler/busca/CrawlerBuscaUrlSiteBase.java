package br.ml.api.crawler.busca;

public interface CrawlerBuscaUrlSiteBase {

	String getSiteBase();
	ValidarCampos<?> validarCampos();
	
}

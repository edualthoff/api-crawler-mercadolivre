package br.ml.api.crawler.busca;

public enum CrawlerBuscaSiteBase {


	MERCADOLIVRE("mercadolivre");

	private String siteBase;
	
	public String getSiteBase() {
		return siteBase;
	}

	CrawlerBuscaSiteBase(String siteBase) {
		this.siteBase = siteBase;
	}

}

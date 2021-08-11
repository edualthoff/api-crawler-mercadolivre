package br.ml.api.crawler.busca;

public interface ValidarCampos<T> {

	void linkUrl();
	
	void rangeValue();
	
	T build();
}

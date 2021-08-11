package br.spi.edx.auth.custom.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonRootName(value = "ModuloAplicacao")
public class ModuloAplicacao {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nome")
	private String nome;
}

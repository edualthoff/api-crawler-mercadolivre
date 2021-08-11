package br.spi.edx.auth.custom.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonRootName(value = "Pessoa")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Pessoa {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("nome")
	//@Setter(value = AccessLevel.NONE)
	private String nome;

	@JsonProperty("email")
	private String email;

	@JsonProperty("sobrenome")
	//@Setter(value = AccessLevel.NONE)
	private String sobrenome;

	@JsonProperty("session")
	private String session;

	@JsonProperty("usuarioId")
	private String usuarioId;

	@JsonProperty("dataNascimento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	
	@JsonProperty("moduloAplicacao")
	@JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<ModuloAplicacao> moduloAplicacao;
	

}

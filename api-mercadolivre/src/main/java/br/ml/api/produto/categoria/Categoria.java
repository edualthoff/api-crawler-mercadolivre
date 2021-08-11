package br.ml.api.produto.categoria;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.ml.api.config.base.BaseImplementsSQL;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "pr_categoria")
public class Categoria extends BaseImplementsSQL {
	private static final long serialVersionUID = 2844994264684557983L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotBlank(message = "{name.not.blank}")
	@Column(name = "nome")
	@Setter(value = AccessLevel.NONE)
	private String nome;
	@Column(name = "status")
	private boolean status;
	
	public Categoria() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param id
	 * @param tenantID
	 * @param nome
	 * @param status
	 */
	public Categoria(String tenantID, Long id, @NotBlank(message = "{name.not.blank}") String nome, boolean status) {
		super(tenantID);
		this.id = id;
		this.nome = nome;
		this.status = status;
	}
	
	public void setNome(String nome) {
		this.nome = nome.strip().toLowerCase();
	}



}

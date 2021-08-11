package br.ml.api.produto.marca;

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
import lombok.Getter;
import lombok.Setter;




@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "pr_marca")
public class Marca extends BaseImplementsSQL {
	private static final long serialVersionUID = 7805902702815687751L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	@NotBlank(message = "{name.not.blank}")
	@Setter(value = AccessLevel.NONE)
	private String nome;
	//@Field(type = FieldType.Text)
	//private String descricao;

	public Marca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Marca(String tenantID, Long id, @NotBlank(message = "{name.not.blank}") String nome) {
		super(tenantID);
		this.id = id;
		this.nome = nome;
	}

	public void setNome(String nome) {
		this.nome = nome.strip().toLowerCase();
	}


}

package br.ml.api.produto.item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ml.api.config.base.BaseImplementsSQL;
import br.ml.api.produto.categoria.Categoria;
import br.ml.api.produto.marca.Marca;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "pr_item")
public class ItemProduto extends BaseImplementsSQL {
	private static final long serialVersionUID = 676112663977327152L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Setter(value = AccessLevel.NONE)
	@Column(name = "modelo")
	private String modelo;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "status")
	private boolean status;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_pr_categoria", referencedColumnName = "id")
	private Categoria idCategoria;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_pr_marca", referencedColumnName = "id")
	private Marca idMarca;
	

	public void setModelo(String modelo) {
		this.modelo = modelo.strip().toLowerCase();
	}


	/**
	 * @param id
	 * @param tenantID
	 * @param modelo
	 * @param descricao
	 * @param idCategoria
	 * @param idMarca
	 */
	public ItemProduto(Long id, String tenantID, String modelo, String descricao, Categoria idCategoria,
			Marca idMarca) {
		super(tenantID);
		this.id = id;
		this.modelo = modelo;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.idMarca = idMarca;
	}

	/**
	 * 
	 */
	public ItemProduto() {
		super();
	}

	public ItemProduto(String tenantID, Long id, String modelo, String descricao, boolean status, Categoria idCategoria, Marca idMarca) {
		super(tenantID);
		this.id = id;
		this.modelo = modelo;
		this.descricao = descricao;
		this.status = status;
		this.idCategoria = idCategoria;
		this.idMarca = idMarca;
	}	
	
}

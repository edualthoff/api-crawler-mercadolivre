package br.ml.api.crawler.item;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(exclude = {"dateModified"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "crw_item")
//@IdClass(value = CrawlerItemPK.class)
public class CrawlerItem implements Serializable {
	private static final long serialVersionUID = 8783492250521637591L;

	@Data
	@Embeddable
	public static class CrawlerItemPK implements Serializable {
		private static final long serialVersionUID = -8058055081139090125L;

		@Id
		//@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private String id;
		@Id
		//@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "origem")
		private String origem;
		
		public CrawlerItemPK(String id, String origem) {
			super();
			this.id = id;
			this.origem = origem;
		}
		public CrawlerItemPK() {
			super();
		}
	}
	
	@EmbeddedId
	private CrawlerItemPK crawlerItemPK;
	
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "condicao")
	private String condicao;
	@Column(name = "valor")
	@Digits(integer=10, fraction=2)
	private double valor;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "cidade")
	private String cidade;
	@Column(name = "link_url")
	private String linkUrl;
	@Column(name = "tipo_anuncio")
	private String tipoAnuncio;
	@Column(name = "ativo")
	private boolean ativo;
	@Column(name = "date_created")
	@JsonFormat(pattern = "yyyyMMdd'T'HHmmssZ", timezone = "UTC")
	//@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private OffsetDateTime dateCreated;
	@Column(name = "date_modified")
	@LastModifiedDate
	@JsonFormat(pattern = "yyyyMMdd'T'HHmmssZ", timezone = "UTC")
	//@Temporal(TemporalType.TIMESTAMP)
	private OffsetDateTime dateModified;
	

	public CrawlerItem(CrawlerItemPK crawlerItemPK, String titulo, String condicao, @Digits(integer = 10, fraction = 2) double valor,
			String descricao, String cidade, String linkUrl, String tipoAnuncio, boolean ativo) {
		super();
		this.crawlerItemPK = crawlerItemPK;
		this.titulo = titulo;
		this.condicao = condicao;
		this.valor = valor;
		this.descricao = descricao;
		this.cidade = cidade;
		this.linkUrl = linkUrl;
		this.tipoAnuncio = tipoAnuncio;
		this.ativo = ativo;
	}
	
	public CrawlerItem() {
		super();
	}
}

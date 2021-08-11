package br.ml.api.crawler.item;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import br.ml.api.crawler.busca.CrawlerBuscaUrl;
import lombok.Data;




//@IdClass(CrawlerBuscaItemPK.class)
//@EntityListeners(AuditingEntityListener.class)
/*@AttributeOverrides({
    @AttributeOverride(name = "crawlerItemId", column=@Column(name="id_crw_item")),
    @AttributeOverride(name = "origemId", column=@Column(name="origem_crw_item")),
    @AttributeOverride(name = "tenantId", column=@Column(name="tenant_id")),
    @AttributeOverride(name = "crawlerBuscaUrlId", column=@Column(name="id_crw_busca"))
})*/
@Data
@Entity
@Table(name = "crw_busca_item")
public class CrawlerBuscaItem implements Serializable {
	private static final long serialVersionUID = 8292764719632546400L;

	@Data
	@Embeddable
	public static class CrawlerBuscaItemPK implements Serializable {
		private static final long serialVersionUID = -6287507507142984958L;

		//@Id
		//@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "tenant_id")
		private String tenantId;
		//@Id
		@Column(name = "id_crw_busca")
		private Long crawlerBuscaUrlPk;
		//@Id
	    @Embedded 
	    @AttributeOverrides({
	        @AttributeOverride(name="id", column=@Column(name="id_crw_item")),
	        @AttributeOverride(name="origem", column=@Column(name="origem_crw_item")),
	    })  
		private CrawlerItem.CrawlerItemPK crawlerItemPk;

		
		public CrawlerBuscaItemPK() {
			super();
		}
	}
	
	@EmbeddedId
	private CrawlerBuscaItemPK crawlerBuscaItemPK;
	
	@MapsId(value = "crawlerBuscaUrlPk")
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE,CascadeType.PERSIST}, optional = false)
	@JoinColumn(name = "id_crw_busca", referencedColumnName = "id")
	private CrawlerBuscaUrl crawlerBuscaUrl;
	
	@MapsId(value = "crawlerItemPk")
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE,CascadeType.PERSIST}, optional = false)
	@JoinColumns({
		@JoinColumn(name = "id_crw_item", referencedColumnName = "id"),
		@JoinColumn(name = "origem_crw_item", referencedColumnName = "origem")
	})
	private CrawlerItem crawlerItem;
	
	@Column(name = "ativo")
	private boolean ativo;
	
	
	public CrawlerBuscaItem(CrawlerBuscaItemPK crawlerBuscaItemPK, CrawlerBuscaUrl crawlerBuscaUrl, CrawlerItem crawlerItem, boolean ativo) {
		super();
		this.crawlerBuscaItemPK = crawlerBuscaItemPK;
		this.crawlerBuscaUrl = crawlerBuscaUrl;
		this.crawlerItem = crawlerItem;
		this.ativo = ativo;
	}

	public CrawlerBuscaItem() {
		super();
	}

}

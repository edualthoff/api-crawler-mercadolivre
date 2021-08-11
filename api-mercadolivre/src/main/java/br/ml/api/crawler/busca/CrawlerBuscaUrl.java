package br.ml.api.crawler.busca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.ml.api.config.base.BaseImplementsSQL;
import br.ml.api.produto.item.ItemProduto;
import br.ml.api.util.CondicaoProdEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper=false)
//@MappedSuperclass
@Entity
@Table(name = "crw_busca")
public class CrawlerBuscaUrl extends BaseImplementsSQL implements Serializable, CrawlerBuscaUrlSiteBase {
	private static final long serialVersionUID = 8297426447697768554L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;	
	@Column(name = "link_url")
	@NotEmpty
	@Setter(value = AccessLevel.NONE)
	private String linkUrl;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "condicao_produto")
	private CondicaoProdEnum condicao;
	//@Field(type = FieldType.Scaled_Float, scalingFactor = 100)
	@Column(name = "range_inicial")
	@Digits(integer=10, fraction=2)
	private BigDecimal rangeInicial;
	//@Field(type = FieldType.Scaled_Float, scalingFactor = 100)
	@Column(name = "range_final")
	@Digits(integer=10, fraction=2)
	private BigDecimal rangeFinal;
	@Column(name = "status")
	private boolean status;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_pr_item", referencedColumnName = "id" )
	//@NotEmpty
	private ItemProduto idProduto;
	@Column(name = "error_link")
	private boolean errorLink;
	@Column(name = "scheduling_time")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private OffsetDateTime nextDate;
	@Column(name = "hora")
	private long hours;
	@Column(name = "minuto")
	@Setter(value = AccessLevel.NONE)
	private long minutes;
	@Column(name = "site_base")
	private String siteBase;
	
	public CrawlerBuscaUrl() {
		super();
	}
	
	public CrawlerBuscaUrl(@NotEmpty String linkUrl, CondicaoProdEnum condicao, boolean status, 
			@NotEmpty ItemProduto idProduto) {
		this.linkUrl = linkUrl;
		this.condicao = condicao;
		this.status = status;
		this.idProduto = idProduto;
		//this.siteBase = siteBase;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl.toLowerCase();
	}

	@Override
	public ValidarCampos<?> validarCampos() {
		return null;
	}
	
	
	public OffsetDateTime nextSchedulingStart() {
		SchedulingTime scheduling = new SchedulingTime(hours, minutes, nextDate);
		return scheduling.nextDateUpdateOrCreate();
	}

	public void setMinuto(long minuto) {
		SchedulingTime scheduling = new SchedulingTime(hours, minuto, nextDate);
		this.minutes = scheduling.validMinutes();
	}
	
}

package br.ml.api.config.base;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @class Abstract
 * 
 * Base para inserir, alterar, remover um objeto no Postgree
 * - Para Multi Tenant
 * - Contem  @parm date_created, @parm date_modified, @parm tenantID
 * 
 * @author edu
 *
 */
//@EqualsAndHashCode(exclude = {"dateModified"})
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseImplementsSQL implements Serializable {
	private static final long serialVersionUID = 4359702375281289206L;

	@Column(name = "date_created", updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	//@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private OffsetDateTime dateCreated;
	@Column(name = "date_modified", updatable = true)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	//@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private OffsetDateTime dateModified;
	@Column(name = "tenant_id")
	//@JsonIgnore
	private String tenantId;
	
	public BaseImplementsSQL() {
		super();
	}

	public BaseImplementsSQL(String tenantID) {
		super();
		this.tenantId = tenantID;
	}

	public OffsetDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(OffsetDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public OffsetDateTime getDateModified() {
		return dateModified;
	}

	public void setDateModified(OffsetDateTime dateModified) {
		this.dateModified = dateModified;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}


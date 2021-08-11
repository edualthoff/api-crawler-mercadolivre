package br.ml.api.tenant.settings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "tb_settings")
public class TenantSettings implements Serializable {
	private static final long serialVersionUID = -316432127050895605L;
	
	@Id
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "enviar_email_vazio")
	private boolean enviarEmailVazio;

	public TenantSettings(String tenantId, boolean enviarEmailVazio) {
		super();
		this.tenantId = tenantId;
		this.enviarEmailVazio = enviarEmailVazio;
	}

	public TenantSettings() {
		super();
	}
	
}

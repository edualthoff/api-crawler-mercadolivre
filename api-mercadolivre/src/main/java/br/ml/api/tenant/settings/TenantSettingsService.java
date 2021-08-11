package br.ml.api.tenant.settings;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantSettingsService implements Serializable{
	private static final long serialVersionUID = -3076789267678420606L;

	@Autowired
	private TenantSettingsDAO tenantSettingsDAO;
	
	
	public boolean enviarEmailVazio(String idTenant) {
		try {
			return this.tenantSettingsDAO.findById(idTenant).orElseThrow().isEnviarEmailVazio();
		}catch (NoSuchElementException e) {
			return false;
		}
	}
}

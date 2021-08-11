package br.ml.api.tenant.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantSettingsDAO extends JpaRepository<TenantSettings, String>{

}

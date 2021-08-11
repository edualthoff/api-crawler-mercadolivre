package br.ml.api.config.tenant;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;


@Configuration
public class TenantInterceptorService implements HandlerInterceptor {

	@Autowired
	private KeycloakSecurityContext keycloakSecurityContext;

	@Autowired
	private IndexTenantDynamic indexTenant;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//System.out.println("Start preHandle");
		try {
		// ApplicationContextProvider.getApplicationContext().getBean(KeycloakSecurityContext.class);
		if (keycloakSecurityContext == null) {
			//System.out.println("Erro - keycloakSecurityContext null");
			return true;
		}
		// IDToken token = keycloakSecurityContext.getIdToken().getOtherClaims();
		// System.out.println("token: "+keycloakSecurityContext.getTokenString());
		Map<String, Object> customClaims = keycloakSecurityContext.getToken().getOtherClaims();
		if (customClaims.containsKey(IndexTenantDynamic.SESSION_VAR)) {
			//System.out.println("Deu certo session");
			this.indexTenant.manipularSession(customClaims.get(IndexTenantDynamic.SESSION_VAR).toString());
		}
		return true;
		} catch (ClassCastException e) {
			//System.out.println("Erro - keycloakSecurityContext ClassCastException");
			return true;
		}
		/*
		 * throw new ApiNotFoundException(ApiMessageSource.toMessageSetObject(
		 * "tenantID.not.valid", "Link Produto"),
		 * ApiMessageSource.toMessage("tenantID.error.code"),
		 * ApiMessageSource.toMessage("tenantID.error.interceptor"));
		 */
	}

}

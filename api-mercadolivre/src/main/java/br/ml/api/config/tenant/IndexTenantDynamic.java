package br.ml.api.config.tenant;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class IndexTenantDynamic {

	private String index = "";
	private String tenantId;
	private String usuarioId;
	private static String PREFIX_SIMBOL_SESSION = "/";
	static String PREFIX_SIMBOL = "_";
	public static String TENANT_VAR = "tenantId";
	public static String SESSION_VAR = "session";
	
	public IndexTenantDynamic() {
		super();
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantID) {
		this.index = PREFIX_SIMBOL+tenantID;
		this.tenantId = tenantID;
		//System.out.println("setTenantID "+this.index+" "+this.tenantID);
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex() {
		//System.out.println("dynamic "+this.index);
		return index;
	}
	
	public String getUsuarioId() {
		return usuarioId;
	}
	
	public void manipularSession(String session) {
		this.tenantId = session.split(PREFIX_SIMBOL_SESSION)[0];
		this.usuarioId = session.split(PREFIX_SIMBOL_SESSION)[1];
		this.index = PREFIX_SIMBOL+this.tenantId;
		//System.out.println("index: "+this.index);
		
	}
}

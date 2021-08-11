package br.spi.edx.auth.util;

import java.util.Map;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.spi.edx.auth.custom.dto.Pessoa;

public class ExternalProviderService {
	private static final Logger logger = Logger.getLogger(ExternalProviderService.class);

	private ExternalProviderClient externalProviderClient;

	public ExternalProviderService(String url) {
		// String key 
		buildClient(url);
	}

	private void buildClient(String url) {
		ResteasyClient client = new ResteasyClientBuilder()
				// .register(new AuthFilter(key) String key)
				.connectionPoolSize(10).maxPooledPerRoute(5).disableTrustManager().build();
		ResteasyWebTarget target = client.target(url);
		this.externalProviderClient = target.proxyBuilder(ExternalProviderClient.class)
				.classloader(this.getClass().getClassLoader()).build();
	}
	
	public boolean verifieldCodigo(String codigo) {
		boolean value = false;
		System.out.println("Text value code "+codigo+" "+value);
        try {
            value = externalProviderClient.getCodigoVerifield(codigo);
            //user = this.parseUserString(username, userString);
        } catch (Exception e) {
            handleException(e, "getCodigoVerifield");
            System.out.println("Text value code error "+codigo+" "+value);
            return value;
        }
        return value;
	}
	
	public Pessoa createPessoaPrinciapl(Pessoa pessoa, String codigo) {
		Pessoa pessoaNew = null;
        try {
        	Map<String, Object> user = externalProviderClient.postPessoa(codigo, pessoa);
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        	pessoaNew = mapper.convertValue(user.get("pessoa"), Pessoa.class);
        	//System.out.println("RETORNO "+pessoaNew.getNome());
        	//pessoaNew = mapper.readValue(new JSONObject(user.get("pessoa")), Pessoa.class);
        	String session = user.get("session").toString();
        	pessoaNew.setSession(session);
        	//System.out.println("chegoue aqui"+ pessoaNew.getSession());
        	//pessoa = this.parseUserString(username, userString);
        } catch (Exception e) {
            handleException(e, "postPessoa");
        }
        return pessoaNew;
	}
	/*
    private ExternalUser parseUserString(String userId, String userString) {
        logger.info("Got userdata response for userId " + userId + ": " + userString.trim());
        ExternalUser user = null;
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	user = mapper.readValue(userString, ExternalUser.class);
        }catch(Exception e) {
        	logger.info("=========> fail to parse json:" + e.getMessage());
        	return null;
        }
        return user;
    }*/
    
    private void handleException(Exception ex, String serviceName) {
        if (ex instanceof ClientErrorException) {
            ClientErrorException cee = (ClientErrorException) ex;
            Response response = cee.getResponse();
            Response.StatusType statusInfo = response.getStatusInfo();
            logger.error("Error while calling REST-Serivce " + serviceName + ", got response: " + statusInfo.getStatusCode() + " " + statusInfo.getReasonPhrase());
        } else {
            logger.error("Exception while calling REST-Service " + serviceName, ex);
        }
    }
    
    public static ExternalProviderService build() {
    	String url = "http://gateway-service:9020/revenda";
    	return new ExternalProviderService(url);
    }
}

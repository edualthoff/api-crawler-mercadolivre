package br.ml.gateway.excepetion.config;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import br.ml.gateway.excepetion.msg.ApiErrorMessage;


public class ApiErrorAttributes extends DefaultErrorAttributes {

	private String currentApiVersion;
	private String sendReportUri;
	 
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message = "please provide a name";
	
    public ApiErrorAttributes(final String currentApiVersion, final String sendReportUri) {
        this.currentApiVersion = currentApiVersion;
        this.sendReportUri = sendReportUri;
    }
    
	@Override
    public Map<String, Object> getErrorAttributes(final ServerRequest request, final ErrorAttributeOptions options) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(request, options);
        final ApiErrorMessage superHeroAppError = ApiErrorMessage.fromDefaultAttributeMap(
                currentApiVersion, defaultErrorAttributes, sendReportUri
        );
        return superHeroAppError.toAttributeMap();
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

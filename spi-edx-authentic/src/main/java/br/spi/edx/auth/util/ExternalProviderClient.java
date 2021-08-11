package br.spi.edx.auth.util;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.spi.edx.auth.custom.dto.Pessoa;

public interface ExternalProviderClient {

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/pessoa/principal")
    Map<String, Object> postPessoa(@QueryParam("codigo") String codigo, Pessoa pessoa);

    @GET
    @Path("/codigo/verificar")
    boolean getCodigoVerifield(@QueryParam("codigo") String codigo);

    @GET
    @Path("/modulos")
    List<String> getListModulos(@QueryParam("codigo") String codigo);
}

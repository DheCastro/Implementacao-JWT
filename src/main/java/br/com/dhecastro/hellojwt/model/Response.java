package br.com.dhecastro.hellojwt.model;

import br.com.dhecastro.hellojwt.resource.HelloResource;

/**
 * Model representativo do objeto retornado em  {@link HelloResource#testehello()}
 * @author dcastro
 *
 */
public class Response {

	private String resposta;

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
}

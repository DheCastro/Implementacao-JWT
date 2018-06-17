package br.com.dhecastro.hellojwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtro que intercepta as requisições que necessitam de autenticação 
 * e verifica se o token é válido, permitindo as mesmas
 * @author dcastro
 *
 */
public class FiltroDeAutenticacaoJWT extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		//Manda a requisição para o serviço de autenticação do token
		//e verifica a presença do mesmo no request
		Authentication authentication = ServicoDeAutenticacaoDoToken
				.verificaAutenticacao((HttpServletRequest) request);
		
		//Seta a autenticação no context de segurança da aplicação
		//Se não encontrar o token no request, seta nulo
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}

package br.com.dhecastro.hellojwt.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dhecastro.hellojwt.model.AccountCredentials;

/**
 * Filtro que recebe a requisição de login do usuário e retorna o token de acesso
 * @author dcastro
 *
 */
public class FiltroDeLoginJWT extends AbstractAuthenticationProcessingFilter {

	public FiltroDeLoginJWT(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

	/**
	 * Método que captura os dados da requisição de login e verifica se o usuário é válido.
	 * Caso seja um usuário válido, chama o método successfulAuthentication
	 */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.<GrantedAuthority>emptyList()
                )
        );
    }

    /**
     * Método que chama o serviço de autenticação por token para o mesmo adiconar um JWT ao response
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth
    ) throws IOException, ServletException {
        
    	ServicoDeAutenticacaoDoToken.adicionaTokenAutenticacao(res, auth.getName());
    }

}
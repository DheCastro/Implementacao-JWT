package br.com.dhecastro.hellojwt.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Serviço de verificação do token, usuário e senha
 * @author dcastro
 *
 */
public class ServicoDeAutenticacaoDoToken {

	//Em milissegundos (Tempo atual é de 10 dias)
	private static final long TEMPO_DE_EXPIRACAO = 864000000;
    private static final String SECRET = "MySecreteApp";
    private static final String PREFIXO_TOKEN = "Bearer";
    private static final String ATRIBUTO_HEADER = "Authorization";

    /**
     * Método que recebe o request de login e adiciona o token ao response para devolver ao usuário
     * @param res
     * @param username
     */
    public static void adicionaTokenAutenticacao(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TEMPO_DE_EXPIRACAO))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String token = PREFIXO_TOKEN + " " + JWT;
        res.addHeader(ATRIBUTO_HEADER, token);

        try {
            res.getOutputStream().print(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que recebe o token e retorna um objeto Authentication para ser adicionado ao contexto de segurança do Spring
     * @param token
     * @return
     */
    public static Authentication autenticaPorToken(String token) {
        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(PREFIXO_TOKEN, ""))
                .getBody()
                .getSubject();

        return user != null ? new UsernamePasswordAuthenticationToken(user, null, null) : null;
    }

    /**
     * Método que recebe o request aos recursos e verifica a presença do token
     * @param request
     * @return
     */
    public static Authentication verificaAutenticacao(HttpServletRequest request) {
        String token = request.getHeader(ATRIBUTO_HEADER);
        if (token != null) {
            return autenticaPorToken(token);
        }
        return null;
    }
}

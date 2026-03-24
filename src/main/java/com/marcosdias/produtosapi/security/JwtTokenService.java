package com.marcosdias.produtosapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenService {

	@Value("${app.jwt.secret}")
	private String secret;

	@Value("${app.jwt.expiration-ms:3600000}")
	private long expirationMs;

	private Key signingKey;

	@PostConstruct
	public void init() {
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String gerarToken(String email) {
		Date agora = new Date();
		Date expiraEm = new Date(agora.getTime() + expirationMs);

		return Jwts.builder().setSubject(email).setIssuedAt(agora).setExpiration(expiraEm)
				.signWith(signingKey, SignatureAlgorithm.HS256).compact();
	}

	public boolean tokenValido(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public String extrairEmail(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
}

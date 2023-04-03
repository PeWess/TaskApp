package com.example.springboottaskmanager.security.authconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    /**
     * Секретный код для шифрования ключей.
     */
    @Value(value = "${secret_code}")
    private String secretCode;

    /**
     * Метод для извлечения из токена имени пользователя.
     * @param token Содержимое токена.
     * @return Заявку(Claim) с имненем пользователя
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Метод, извлекающий все заявки(claims) из токена.
     * @param token Содержимое токена.
     * @param claimsResolver
     * @return Список заявок(claims).
     * @param <T>
     */
    public <T> T extractClaim(
            final String token,
            final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Метод, возвращающий новый токен для пользователя.
     * @param userDetails
     * @return JWT-токен.
     */
    public String generateToken(final UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Метод, генерирующий новый токен пользователя.
     * @param extraClaims Список заявок(claims)
     * @param userDetails
     * @return JWT-токен.
     */
    public String generateToken(
            final Map<String, Object> extraClaims,
            final UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Метод проверяет, валиден ли токен пользователя.
     * @param token Токен.
     * @param userDetails
     * @return Boolean, указывающий, валиден ли токен.
     */
    public boolean isTokenValid(
            final String token,
            final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretCode);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

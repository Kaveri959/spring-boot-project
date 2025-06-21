package com.app.hospital.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expiryMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /* -------- generateToken omitted for brevity -------- */

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                   .parseClaimsJws(token).getBody().getSubject();
    }

    /** ⭐ NEW: pull role claim */
    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                   .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public boolean validateToken(String t, String username) {
        try { return extractUsername(t).equals(username); }
        catch (JwtException e) { return false; }
    }

   public String generateToken(String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("role", role)  // include role claim!
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiryMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
}

}

package com.openclassrooms.mddapi.token;

import com.openclassrooms.mddapi.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        // Création des données personnalisées à inclure dans le token (ici, le rôle)
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();// Génération finale du token
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();// Récupération du corps du token (les claims)
        return Long.parseLong(claims.getSubject());// L'ID est stocké dans "subject"
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role"); // ✅ le rôle est dans les claims
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // Signature JWT invalide
        } catch (MalformedJwtException ex) {
            // Token JWT mal formé
        } catch (ExpiredJwtException ex) {
            // Token JWT expiré
        } catch (UnsupportedJwtException ex) {
            // Token JWT non supporté
        } catch (IllegalArgumentException ex) {
            // Claims JWT vide
        }
        return false;
    }
}

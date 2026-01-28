package com.bookmyshow.main.util;

import com.bookmyshow.security.model.ClientAuthConfig;
import com.bookmyshow.security.repository.ClientAuthConfigRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class JwtUtil {

    @Autowired
    ClientAuthConfigRepository clientAuthConfigRepository;

    public static String generateToken(Map<String, Object> claims, String subject, long expiryMillis,
                                       String secretKey, String clientId) {
        return Jwts.builder()
                .setHeaderParam("clientId", clientId)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String extractClientIdFromHeader(String token) {
        // Extract header without verification
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        String headerJson = new String(java.util.Base64.getUrlDecoder().decode(parts[0]));

        return extractJsonValue(headerJson, "clientId");
    }

    private static String extractJsonValue(String json, String key) {

        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex == -1) return null;

        int valueStart = json.indexOf("\"", keyIndex + searchKey.length() + 1) + 1;
        int valueEnd = json.indexOf("\"", valueStart);
        return json.substring(valueStart, valueEnd);
    }

    public static Claims extractAllClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public static <T> T extractClaim(String token, String secretKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    public static String extractSubject(String token, String secretKey) {
        return extractClaim(token, secretKey, Claims::getSubject);
    }

    public static Date extractExpiration(String token, String secretKey) {
        return extractClaim(token, secretKey, Claims::getExpiration);
    }

    public static List<String> extractPermissions(String token, String secretKey) {
        Claims claims = extractAllClaims(token, secretKey);
        return claims.get("permissions", List.class);
    }

    public static boolean isTokenExpired(String token, String secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

    public static boolean validateToken(String token, String secretKey, String username) {
        final String extractedUsername = extractSubject(token, secretKey);
        return (extractedUsername.equals(username) && !isTokenExpired(token, secretKey));
    }
}
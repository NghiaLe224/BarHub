package com.mingles.web.util;

import com.mingles.web.entity.RoleEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    public static String generateToken(String username, Set<RoleEntity.RoleName> roles) {
        List<String> roleNames = roles.stream().map(Enum::name).toList();
        return Jwts.builder().setSubject(username)
                .claim("roles", roleNames)
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    public static String generateRefreshToken(String username) {
        return Jwts.builder().setSubject(username)
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 7)) // 7 days
                .compact();
    }


    public static String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static LocalDateTime extractExpiration(String token) {
         Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

    }

    @SuppressWarnings("unchecked") // don't know what type of List : String or Object ,..
    public List<String> extractRoles(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }
}

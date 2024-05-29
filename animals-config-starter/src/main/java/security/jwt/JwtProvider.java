package security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    private final String jwtAccessSecret;
    private final int jwtExpiration;

    public JwtProvider(
            @Value("${spring.security.secret.access}") String jwtAccessSecret,
            @Value("${spring.security.secret.expiration}") int jwt_expiration
    ) {
        this.jwtAccessSecret = jwtAccessSecret;
        jwtExpiration = jwt_expiration;
    }

    public String generateAccessToken(@NonNull Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusSeconds(jwtExpiration).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(accessExpiration)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(@NonNull String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(authToken);
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            LOGGER.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            LOGGER.error("invalid token", e);
        }
        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }
}

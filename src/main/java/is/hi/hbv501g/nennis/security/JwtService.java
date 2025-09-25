package is.hi.hbv501g.nennis.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class JwtService {
    private static final Logger logger = Logger.getLogger(JwtService.class.getName());
    private final Key key;

    public JwtService(@Value("${app.jwt.secret}") String secret, Environment env) {
        // Print where the property is coming from
        for (PropertySource<?> ps : ((ConfigurableEnvironment) env).getPropertySources()) {
            Object v = ps.getProperty("app.jwt.secret");
            if (v != null) {
                System.out.println("[jwt] source=" + ps.getName()
                        + " length=" + v.toString().trim().length());
            }
        }
        this.key = buildKey(secret); // your buildKey method that enforces >= 32 bytes
    }

//    public JwtService(
//            @Value("app.jwt.secret") String secret,
//            @Value("${app.jwt.expiration}") long expirationMs) {
//
//        this.key = buildKey(secret);
//        this.expirationMs = expirationMs;
//
//        System.out.println("JWT key length (bytes) = " + key.getEncoded().length);
//    }

    static Key buildKey(String secret) {
        byte[] bytes;
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("app.jwt.secret is empty");
        }
        String trimmed = secret.trim();
        if (trimmed.startsWith("base64:")) {
            String b64 = trimmed.substring("base64:".length());
            bytes = Decoders.BASE64.decode(b64);
        } else {
            bytes = trimmed.getBytes(StandardCharsets.UTF_8);
        }
        if (bytes.length < 32) { // 256 bits
            throw new IllegalArgumentException("JWT secret must be at least 256 bits (32 bytes). Was " + (bytes.length * 8) + " bits.");
        }
        return Keys.hmacShaKeyFor(bytes);
    }

    public Key getKey() {
        return key;
    }

    /**
     * Generates a JWT token for a subject (username)
     * @param subjectEmail The subject (username) to include in the token
     * @return A JWT token as a String
     */
    public String generateToken(String subjectEmail) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subjectEmail)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a JWT token
     * @param token The JWT token
     * @return The username extracted from the token
     */
    public String extractSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates a JWT token
     * @param token The JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Log the exception if needed
            return false;
        }
    }
}

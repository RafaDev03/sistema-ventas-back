package sysventa.sistema_ventas_back.util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import sysventa.sistema_ventas_back.entities.RefreshToken;
import sysventa.sistema_ventas_back.repository.RefreshTokenRepository;

/**
 * JwtUtils
 */

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15 minutes
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 days

    public String createToken(Authentication authentication) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            String username = authentication.getPrincipal().toString();
            String authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            String JwtToken = JWT.create()
                    .withIssuer(this.userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY))
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);
            return JwtToken;
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error al generar el token", null);
        }
    }

    public String createRefreshToken(String username) {
        String refreshToken = createRefreshToken(username, JWT_REFRESH_TOKEN_VALIDITY);
        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUsername(username);
        token.setExpiryDate(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY));
        refreshTokenRepository.save(token);
        return refreshToken;

    }

    private String createRefreshToken(String username, long validity) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            return JWT.create()
                    .withIssuer(this.userGenerator)
                    .withSubject(username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + validity))
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error al generar el token", e);
        }
    }

    public DecodedJWT validateToken(String token) {
        try {
            DecodedJWT decodedJWT;
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalido, no autorizado");
        }

    }

    public String extracUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaim(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

    @Transactional
    public void deleteRefreshToken(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }

}
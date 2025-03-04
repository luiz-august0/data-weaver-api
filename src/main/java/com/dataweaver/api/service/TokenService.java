package com.dataweaver.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.dataweaver.api.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private final String databasePasswordClaim = "database-pass";

    private final String sessionIssuer = "dataweaver-session";

    private Instant genExpirationDateAccessToken() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genExpirationDateRefreshToken() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genExpirationDateRecoveryToken() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    private String handleValidateTokenException(Supplier<String> supplier, Boolean recovery) {
        try {
            return supplier.get();
        } catch (Exception e) {
            if (recovery) {
                throw new ApplicationGenericsException(EnumGenericsException.EXPIRED_RECOVERY);
            }

            if (e.getClass().equals(TokenExpiredException.class)) {
                throw new ApplicationGenericsException(EnumGenericsException.EXPIRED_SESSION);
            } else if (e.getClass().equals(JWTVerificationException.class)) {
                throw new ApplicationGenericsException(EnumGenericsException.VALIDATE_TOKEN);
            } else {
                throw new ApplicationGenericsException(e.getMessage());
            }
        }
    }

    public String generateToken(User user, String databasePassword) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withAudience(user.getSchema())
                    .withIssuer(sessionIssuer)
                    .withSubject(user.getLogin())
                    .withClaim(databasePasswordClaim, databasePassword)
                    .withExpiresAt(genExpirationDateAccessToken())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new ApplicationGenericsException(EnumGenericsException.GENERATE_TOKEN);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            String uniqueId = UUID.randomUUID().toString();
            String userIdAndSchema = user.getId() + "-" + user.getSchema();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withJWTId(uniqueId)
                    .withIssuer(sessionIssuer)
                    .withSubject(new String(Base64.getEncoder().encode(userIdAndSchema.getBytes())))
                    .withExpiresAt(genExpirationDateRefreshToken())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new ApplicationGenericsException(EnumGenericsException.GENERATE_REFRESH_TOKEN);
        }
    }

    public String generateRecovery(User user) {
        try {
            String uniqueId = UUID.randomUUID().toString();
            String userIdAndSchema = user.getId() + "-" + user.getSchema();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withJWTId(uniqueId)
                    .withIssuer(sessionIssuer)
                    .withSubject(new String(Base64.getEncoder().encode(userIdAndSchema.getBytes())))
                    .withExpiresAt(genExpirationDateRecoveryToken())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new ApplicationGenericsException(EnumGenericsException.GENERATE_RECOVERY_TOKEN);
        }
    }

    private String handleValidateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer(sessionIssuer)
                .build()
                .verify(token)
                .getSubject();
    }

    public String validateToken(String token) {
        return handleValidateTokenException(() -> handleValidateToken(token), false);
    }

    public String validateRecoveryToken(String token) {
        return handleValidateTokenException(() -> handleValidateToken(token), true);
    }

    public String getAudienceFromToken(String token) {
        try {
            return JWT.decode(token)
                    .getAudience()
                    .getFirst();
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public String getDatabasePasswordClaimFromToken(String token) {
        try {
            return JWT.decode(token).getClaim(databasePasswordClaim).asString();
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

}
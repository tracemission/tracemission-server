package org.wirvsvirushackathon.configuration.security;

import java.time.Duration;
import java.util.UUID;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.wirvsvirushackathon.model.Role;

import javax.inject.Singleton;

@Singleton
public class TokenGenerator {

    public String generateToken(UUID id, Role role) {
        JwtClaimsBuilder jwtClaimsBuilder = buildClaims(id, role);
        Duration timeClaims = Duration.ofDays(90);
        String token = "error during generation";
        try {
            token = TokenUtils.generateTokenString(jwtClaimsBuilder, timeClaims);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    private JwtClaimsBuilder buildClaims(UUID id, Role role) {
        return Jwt.claims()
                .subject(id.toString())
                .issuer("https://tracemission.app/")
                .claim("role", role.toString());
    }

}
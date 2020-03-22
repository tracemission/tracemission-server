package org.wirvsvirushackathon.configuration.security;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.wirvsvirushackathon.configuration.environment.TokenEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GenerateToken {

    @Inject
    private TokenEnvironment tokenEnvironment;

    public String generateToken(UUID id) throws JOSEException {
        if (!tokenEnvironment.getSecret().isPresent()) return null;
        byte[] secret = getSecret();
        JWSSigner signer = new MACSigner(secret);
        JWTClaimsSet jwtClaimsSet = buildClaims(id);
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS512),
                jwtClaimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    private JWTClaimsSet buildClaims(UUID id) {
        return new JWTClaimsSet.Builder()
                .subject(id.toString())
                .issuer("https://tracemission.app/")
                .expirationTime(Date.from(Instant.now().plus(Duration.ofDays(90))))
                .build();
    }

    private byte[] getSecret() {
        String key = tokenEnvironment.getSecret().get();
        return key.getBytes();
    }

}
package com.stock_portfolio.stock_portfolio_api.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.*;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Service
public class GoogleTokenVerifier {

    private static final String GOOGLE_CERT_URL = "https://www.googleapis.com/oauth2/v3/certs";
    private static final String ISSUER = "https://accounts.google.com";
    private static final String CLIENT_ID = "507156469512-bdnoqanvpa9mjae8jnb17rpotcchndcm.apps.googleusercontent.com";

    public JWTClaimsSet verify(String idToken) throws Exception {

        // 1. parse token
        SignedJWT signedJWT = SignedJWT.parse(idToken);

        // 2. load Google public keys
        JWKSet jwkSet = JWKSet.load(new URL(GOOGLE_CERT_URL));

        // 3. หา key ที่ตรงกับ token
        JWSHeader header = signedJWT.getHeader();
        JWK jwk = jwkSet.getKeyByKeyId(header.getKeyID());

        if (jwk == null) {
            throw new RuntimeException("Invalid key ID");
        }

        RSAKey rsaKey = (RSAKey) jwk;
        RSAPublicKey publicKey = rsaKey.toRSAPublicKey();

        // 4. verify signature
        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        if (!signedJWT.verify(verifier)) {
            throw new RuntimeException("Invalid signature");
        }

        // 5. get claims
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        // 6. validate claims
        if (!ISSUER.equals(claims.getIssuer())) {
            throw new RuntimeException("Invalid issuer");
        }

        if (!claims.getAudience().contains(CLIENT_ID)) {
            throw new RuntimeException("Invalid audience");
        }

        if (claims.getExpirationTime().before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        return claims;
    }
}

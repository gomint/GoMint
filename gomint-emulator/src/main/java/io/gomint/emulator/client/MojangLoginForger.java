/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator.client;

import io.gomint.server.jwt.JwtAlgorithm;
import io.gomint.server.jwt.JwtSignatureException;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

/**
 * Helper class to create a non-authenticated JWT chain.
 *
 * @author BlackyPaw
 * @version 1.0
 */
@Setter
public class MojangLoginForger {

    private String username;
    @Getter private UUID uuid;
    private PublicKey publicKey;
    private JSONObject skinData;
    private String xuid;

    @SuppressWarnings( "unchecked" )
    public String forge( PrivateKey privateKey ) {
        final JwtAlgorithm algorithm = JwtAlgorithm.ES384;

        // Convert our public key to Base64:
        String publicKeyBase64 = Base64.getEncoder().encodeToString( this.publicKey.getEncoded() );

        // Construct JSON WebToken:
        JSONObject header = new JSONObject();
        header.put( "alg", algorithm.getJwtName() );
        header.put( "x5u", publicKeyBase64 );

        long timestamp = System.currentTimeMillis() / 1000;

        JSONObject claims = new JSONObject();
        claims.put( "nbf", timestamp - 1 );
        claims.put( "exp", timestamp + 24 * 60 * 60 );
        claims.put( "iat", timestamp + 24 * 60 * 60 );
        claims.put( "iss", "self" );
        claims.put( "certificateAuthority", true );
        // claims.put( "randomNonce", ThreadLocalRandom.current().nextInt() );

        JSONObject extraData = new JSONObject();
        extraData.put( "displayName", this.username );
        extraData.put( "identity", this.uuid.toString() );
        extraData.put( "proxprox.xuid", this.xuid );
        extraData.put( "xuid", "" ); // Because dylan forgot to check for NULL, poor dylan

        claims.put( "extraData", extraData );
        claims.put( "identityPublicKey", publicKeyBase64 );

        StringBuilder builder = new StringBuilder();
        builder.append( Base64.getUrlEncoder().encodeToString( header.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );
        builder.append( '.' );
        builder.append( Base64.getUrlEncoder().encodeToString( claims.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );

        // Sign the token:
        byte[] signatureBytes = builder.toString().getBytes( StandardCharsets.US_ASCII );
        byte[] signatureDigest;
        try {
            signatureDigest = algorithm.getSignature().sign( privateKey, signatureBytes );
        } catch ( JwtSignatureException e ) {
            e.printStackTrace();
            return null;
        }

        builder.append( '.' );
        builder.append( Base64.getUrlEncoder().encodeToString( signatureDigest ) );

        return builder.toString();
    }

    @SuppressWarnings( "unchecked" )
    public String forgeSkin( PrivateKey privateKey ) {
        final JwtAlgorithm algorithm = JwtAlgorithm.ES384;

        // Convert our public key to Base64:
        String publicKeyBase64 = Base64.getEncoder().encodeToString( this.publicKey.getEncoded() );

        // Construct JSON WebToken:
        JSONObject header = new JSONObject();
        header.put( "alg", algorithm.getJwtName() );
        header.put( "x5u", publicKeyBase64 );

        StringBuilder builder = new StringBuilder();
        builder.append( Base64.getUrlEncoder().encodeToString( header.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );
        builder.append( '.' );
        builder.append( Base64.getUrlEncoder().encodeToString( this.skinData.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );

        // Sign the token:
        byte[] signatureBytes = builder.toString().getBytes( StandardCharsets.US_ASCII );
        byte[] signatureDigest;
        try {
            signatureDigest = algorithm.getSignature().sign( privateKey, signatureBytes );
        } catch ( JwtSignatureException e ) {
            e.printStackTrace();
            return null;
        }

        builder.append( '.' );
        builder.append( Base64.getUrlEncoder().encodeToString( signatureDigest ) );

        return builder.toString();
    }

    public void setSkinData( JSONObject skinData ) {
        this.skinData = skinData;
    }

}

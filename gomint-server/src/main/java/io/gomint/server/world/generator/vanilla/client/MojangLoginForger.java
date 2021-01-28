/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla.client;

import io.gomint.server.jwt.JwtAlgorithm;
import io.gomint.server.jwt.JwtSignatureException;
import org.json.simple.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Helper class to create a non-authenticated JWT chain.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class MojangLoginForger {

    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    private String username;
    private UUID uuid;
    private PublicKey publicKey;
    private JSONObject skinData;
    private String xuid;

    public UUID uuid() {
        return this.uuid;
    }

    public void username(String username) {
        this.username = username;
    }

    public void uuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void publicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void xuid(String xuid) {
        this.xuid = xuid;
    }

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
        extraData.put( "XUID", this.xuid );
        extraData.put( "titleId", "1739947436" );

        claims.put( "extraData", extraData );
        claims.put( "identityPublicKey", publicKeyBase64 );

        StringBuilder builder = new StringBuilder();
        builder.append( ENCODER.encodeToString( header.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );
        builder.append( '.' );
        builder.append( ENCODER.encodeToString( claims.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );

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
        builder.append( ENCODER.encodeToString( signatureDigest ) );

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
        builder.append( ENCODER.encodeToString( header.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );
        builder.append( '.' );
        builder.append( ENCODER.encodeToString( this.skinData.toJSONString().getBytes( StandardCharsets.UTF_8 ) ) );

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
        builder.append( ENCODER.encodeToString( signatureDigest ) );

        return builder.toString();
    }

    public void skinData(JSONObject skinData ) {
        this.skinData = skinData;
    }

}

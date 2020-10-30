package io.gomint.server.jwt;

import io.gomint.server.util.StringUtil;
import org.json.simple.JSONObject;

import java.security.Key;
import java.util.Base64;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EncryptionRequestForger {

    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    /**
     * Forge a new Encryption start JWT token
     *
     * @param serverPublic  Public key of the server (x5u field)
     * @param serverPrivate Private key to sign the JWT with
     * @param clientSalt    Client salt for the claim payload
     * @return a signed and ready to be sent JWT token
     */
    @SuppressWarnings( "unchecked" )
    public String forge( String serverPublic, Key serverPrivate, byte[] clientSalt ) {
        final JwtAlgorithm algorithm = JwtAlgorithm.ES384;

        // Construct JSON WebToken:
        JSONObject header = new JSONObject();
        header.put( "alg", algorithm.getJwtName() );
        header.put( "x5u", serverPublic );

        // Construct claims (payload):
        JSONObject claims = new JSONObject();
        claims.put( "salt", Base64.getEncoder().encodeToString( clientSalt ) );

        // Build it together
        StringBuilder builder = new StringBuilder();
        builder.append( ENCODER.encodeToString( StringUtil.getUTF8Bytes( header.toJSONString() ) ) );
        builder.append( '.' );
        builder.append( ENCODER.encodeToString( StringUtil.getUTF8Bytes( claims.toJSONString() ) ) );

        // Sign the token:
        byte[] signatureBytes = StringUtil.getUTF8Bytes( builder.toString() );
        byte[] signatureDigest;

        try {
            signatureDigest = algorithm.getSignature().sign( serverPrivate, signatureBytes );
        } catch ( JwtSignatureException e ) {
            e.printStackTrace();
            return null;
        }

        builder.append( '.' );
        builder.append( ENCODER.encodeToString( signatureDigest ) );

        return builder.toString();
    }

}

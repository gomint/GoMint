package io.gomint.server.jwt;

import io.gomint.server.util.StringUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Map;

/**
 * Minimalistic JWT library for use with Mojang's authentication mechanism.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class JwtToken {

    @SuppressWarnings( "unchecked" )
    public static JwtToken parse( String s ) {
        String[] split = s.split( "\\." );
        if ( split.length != 3 ) {
            throw new IllegalArgumentException( "Invalid JWT Token: Expecting exactly three parts delimited by dots '.'" );
        }

        String jwtHeaderJson = new String( Base64.getDecoder().decode( split[0] ), StandardCharsets.UTF_8 );
        String jwtClaimsJson = new String( Base64.getDecoder().decode( split[1] ), StandardCharsets.UTF_8 );

        JSONParser parser = new JSONParser();
        Object jwtHeaderRaw;
        Object jwtClaimsRaw;
        try {
            jwtHeaderRaw = parser.parse( jwtHeaderJson );
            jwtClaimsRaw = parser.parse( jwtClaimsJson );

            if ( !( jwtHeaderRaw instanceof JSONObject ) || !( jwtClaimsRaw instanceof JSONObject ) ) {
                throw new ParseException( ParseException.ERROR_UNEXPECTED_TOKEN );
            }
        } catch ( ParseException e ) {
            throw new IllegalArgumentException( "Invalid JWT Token: Expected Base-64 encoded JSON data" );
        }

        JwtToken token = new JwtToken();
        token.header = new JwtHeader( (JSONObject) jwtHeaderRaw );
        token.claims = (JSONObject) jwtClaimsRaw;
        token.signatureBytes = StringUtil.getUTF8Bytes( split[0] + '.' + split[1] );
        token.signatureDigest = Base64.getUrlDecoder().decode( split[2] );

        return token;
    }

    private JwtHeader header;
    private Map<String, Object> claims;

    /*
     * We are caching the signature bytes for the following reason:
     * In case we would re-serialize the header and claims when validating the token we might
     * end up with the resulting JSON string's members ordered differently than in the original
     * string. This would cause the validation to fail as the signature was calculatedusing a
     * different string than the one we serailized even though the 'meaning' of said string remains
     * the same.
     */
    private byte[] signatureBytes;
    private byte[] signatureDigest;

    private JwtToken() {

    }

    /**
     * Returns the JwtHeader associated with this JwtToken. May be used to query additional information.
     *
     * @return The JwtHeader associated with this JwtToken
     */
    public JwtHeader getHeader() {
        return this.header;
    }

    /**
     * Gets a named property from the JwtToken's claim field if any such property exists.
     *
     * @param <T> Type of object
     * @param key The name of the property to get
     * @return The property if found or null otherwise
     */
    public <T> T getClaim( String key ) {
        return (T) this.claims.get( key );
    }

    /**
     * Gets a named property from the JwtToken's claim field if any such property exists and is of the specified type.
     *
     * @param clazz The clazz the property is expected to be of
     * @param key   The name of the property to get
     * @param <T>   The generic type matching the {@code clazz} parameter
     * @return The property if found or null otherwise
     */
    public <T> T getClaim( Class<T> clazz, String key ) {
        Object value = this.claims.get( key );
        if ( value == null || !clazz.isAssignableFrom( value.getClass() ) ) {
            return null;
        }
        return clazz.cast( value );
    }

    /**
     * Verifies this token's signatureDigest using the given secret. Beware of the intricacies explained in this article
     * by Tim McLean (https://auth0.com/blog/critical-vulnerabilities-in-json-web-token-libraries/).
     * <p>
     * In order to use this method the {@code alg} field must be present on the token's header.
     *
     * @param key The key used to sign this token
     * @return Whether or not this token could be verified using the given secret
     * @throws JwtSignatureException Thrown in case the signature could not be validated at all
     */
    public boolean validateSignature( Key key ) throws JwtSignatureException {
        JwtAlgorithm algorithm = this.header.getAlgorithm();
        return ( algorithm != null && this.validateSignature( algorithm, key ) );
    }

    /**
     * Verifies this token's signatureDigest using the given secret. Beware of the intricacies explained in this article
     * by Tim McLean (https://auth0.com/blog/critical-vulnerabilities-in-json-web-token-libraries/).
     * <p>
     * In order to use this method the {@code alg} field must be present on the token's header.
     *
     * @param algorithm The algorithm to use for validating the token's signatureDigest
     * @param key       The key used to sign this token
     * @return Whether or not this token could be verified using the given secret
     * @throws JwtSignatureException Thrown in case the signature could not be validated at all
     */
    public boolean validateSignature( JwtAlgorithm algorithm, Key key ) throws JwtSignatureException {
        JwtSignature validator = algorithm.getSignature();
        return validator.validate( key, this.signatureBytes, this.signatureDigest );
    }

}

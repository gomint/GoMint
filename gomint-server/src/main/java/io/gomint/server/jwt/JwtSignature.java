/*
 *  Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jwt;

import java.security.Key;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public interface JwtSignature {
	
	/**
	 * Validates a signature using a pre-specified algorithm.
	 * <p>
	 * This method takes a Key as its arguments even though it only supports ES384 which requires a PublicKey instead
	 * and got to upcast in order to ensure it has been passed one. This is done as other algorithms allowed per JWT
	 * standard such as Hmac256 might require a regular Key in case Mojang should ever decide to use them (would not
	 * make sense, though).
	 *
	 * @param key       The key to be used to verify the signature
	 * @param signature The signature to be verified
	 * @param digest    The digest appended to a JWT token as proof
	 *
	 * @return Whether or not the signature could be validated successfully
	 * @throws JwtSignatureException Thrown in case the signature could not be validated
	 */
	boolean validate( Key key, byte[] signature, byte[] digest ) throws JwtSignatureException;
	
	/**
	 * Signs the given signature bytes and returns the encoded digest.
	 * <p>
	 * See {@link #validate(Key, byte[], byte[])} as for why this method uses Key as type for the key parameter.
	 *
	 * @param key       The key to be used to sign the signature
	 * @param signature The signature to be verified
	 *
	 * @return The digest to be appended to a JWT token as proof
	 * @throws JwtSignatureException Thrown in case the signature bytes could not be signed
	 */
	byte[] sign( Key key, byte[] signature ) throws JwtSignatureException;
	
}

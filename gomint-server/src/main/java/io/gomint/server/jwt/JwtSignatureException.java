/*
 *  Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jwt;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class JwtSignatureException extends Exception {
	
	public JwtSignatureException( String what ) {
		super( what );
	}
	
	public JwtSignatureException( String what, Throwable cause ) {
		super( what, cause );
	}
	
}

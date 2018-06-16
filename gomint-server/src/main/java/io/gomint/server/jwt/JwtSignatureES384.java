/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jwt;

import java.security.*;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class JwtSignatureES384 implements JwtSignature {

    @Override
    public boolean validate( Key key, byte[] signatureBytes, byte[] digestBytes ) throws JwtSignatureException {
        // Create signature and convert to PublicKey:
        Signature signature;
        try {
            signature = Signature.getInstance( "SHA384withECDSA", "BC" );
        } catch ( NoSuchAlgorithmException | NoSuchProviderException e ) {
            throw new JwtSignatureException( "Could not create signature for ES384 algorithm", e );
        }
        if ( !( key instanceof PublicKey ) ) {
            throw new JwtSignatureException( "Signature Key must be a PublicKey for ES384 validation" );
        }
        PublicKey publicKey = (PublicKey) key;

        // Convert ECDSA signature to DER as required by BouncyCastle verifier:
        byte[] derSignature = this.convertConcatRSToDER( digestBytes );

        // Perform actual validation:
        try {
            signature.initVerify( publicKey );
            signature.update( signatureBytes );
            return signature.verify( derSignature );
        } catch ( SignatureException | InvalidKeyException e ) {
            throw new JwtSignatureException( "Could not perform ES384 signature validation", e );
        }
    }

    @Override
    public byte[] sign( Key key, byte[] signatureBytes ) throws JwtSignatureException {
        // Create signature and convert to PrivateKey:
        Signature signature;
        try {
            signature = Signature.getInstance( "SHA384withECDSA", "BC" );
        } catch ( NoSuchAlgorithmException | NoSuchProviderException e ) {
            throw new JwtSignatureException( "Could not create signature for ES384 algorithm", e );
        }
        if ( !( key instanceof PrivateKey ) ) {
            throw new JwtSignatureException( "Signature Key must be a PrivateKey for ES384 signing" );
        }
        PrivateKey privateKey = (PrivateKey) key;

        // Compute DER signature:
        byte[] der;
        try {
            signature.initSign( privateKey );
            signature.update( signatureBytes );
            der = signature.sign();
        } catch ( SignatureException | InvalidKeyException e ) {
            throw new JwtSignatureException( "Could not sign ES384 signature", e );
        }

        return this.convertDERToConcatRS( der );
    }

    /**
     * Converts a DER signature as produced by the BouncyCastle signature signer into the ECSDA's concatenated R+S
     * format.
     *
     * @param der The DER signature to convert
     * @return The converted ECDSA signature
     * @throws JwtSignatureException Thrown in case the specified DER signature was invalid or not suitable for
     *                               ECDSA-384
     */
    private byte[] convertDERToConcatRS( byte[] der ) throws JwtSignatureException {
        // Run some checks on the given DER signature:
        if ( der.length < 8 || der[0] != 0x30 || der[1] > 128 ) {
            throw new JwtSignatureException( "Invalid DER signature" );
        }

        // Detect offsets and length of R and S _WITHOUT_ padding:
        int offsetR = 4;
        int lengthR = der[offsetR - 1];

        int offsetL = offsetR + lengthR + 2;

        if ( der[offsetR] == 0x00 ) {
            offsetR++;
            lengthR--;
        }

        int lengthL = der[offsetL - 1];
        if ( der[offsetL] == 0x00 ) {
            offsetL++;
            lengthL--;
        }

        if ( lengthR > 48 || lengthL > 48 ) {
            throw new JwtSignatureException( "Invalid DER signature for ECSDA 384 bit" );
        }

        // Concatenated signatures must be exactly 96 bytes in size for ES384:
        byte[] concat = new byte[96];

        // Now, as Java neatly fills everything with zeros per default we only need to copy R and S into the correct
        // positions:
        System.arraycopy( der, offsetR, concat, 48 - lengthR, lengthR );
        System.arraycopy( der, offsetL, concat, 96 - lengthL, lengthL );

        return concat;
    }

    /**
     * Converts an ECDSA signature formatted as specified in RFC3278 (https://tools.ietf.org/html/rfc3278#section-8.2)
     * into DER format as required by the BouncyCastle signature verifier.
     *
     * @param concat The ECDSA signature as a byte array
     * @return The converted signature in DER format
     * @throws JwtSignatureException Thrown if the given signature is not a valid ECDSA signature
     */
    private byte[] convertConcatRSToDER( byte[] concat ) throws JwtSignatureException {
        if ( concat.length != 96 ) {
            // ECDSA signature for ECDH384 must be exactly 96 bytes long if specified in Concatenated format:
            throw new JwtSignatureException( "Invalid ECDSA signature (expected 96 bytes, got " + concat.length + ")" );
        }

        int rawLength = concat.length >> 1;

        int offsetR = 0;
        while ( concat[offsetR] == 0x00 ) {
            offsetR++;
        }
        int lengthR = rawLength - offsetR;
        boolean padR = ( concat[offsetR] & 0x80 ) != 0;

        int offsetL = rawLength;
        while ( concat[offsetL] == 0x00 ) {
            offsetL++;
        }
        int lengthL = ( rawLength << 1 ) - offsetL;
        boolean padL = ( concat[offsetL] & 0x80 ) != 0;

        int sigLength = 2 + lengthR + ( padR ? 1 : 0 ) + 2 + lengthL + ( padL ? 1 : 0 );

        int cursor = 0;
        byte[] derSignature = new byte[2 + sigLength];
        derSignature[cursor++] = 0x30;
        derSignature[cursor++] = (byte) sigLength;

        derSignature[cursor++] = 0x02;
        derSignature[cursor++] = (byte) ( lengthR + ( padR ? 1 : 0 ) );
        // Leaves the potentially required 0x00 padding byte untouched:
        if ( padR ) {
            cursor++;
        }
        System.arraycopy( concat, offsetR, derSignature, cursor, lengthR );
        cursor += lengthR;

        derSignature[cursor++] = 0x02;
        derSignature[cursor++] = (byte) ( lengthL + ( padL ? 1 : 0 ) );
        // Leaves the potentially required 0x00 padding byte untouched:
        if ( padL ) {
            cursor++;
        }
        System.arraycopy( concat, offsetL, derSignature, cursor, lengthL );
        cursor += lengthL;

        if ( cursor != derSignature.length ) {
            throw new JwtSignatureException( "COuld not convert ECDSA signature to DER format" );
        }

        return derSignature;
    }

}

/*
 *  Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Handles all encryption needs of the Minecraft Pocket Edition Protocol (ECDH Key Exchange and
 * shared secret generation).
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class EncryptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger( EncryptionHandler.class );
    private static final ThreadLocal<MessageDigest> SHA256_DIGEST = new ThreadLocal<>();

    // Packet counters
    private AtomicLong sendingCounter = new AtomicLong( 0 );
    private AtomicLong receiveCounter = new AtomicLong( 0 );

    // Client Side:
    private ECPublicKey clientPublicKey;
    private Cipher clientEncryptor;
    private Cipher clientDecryptor;

    // Data for packet and checksum calculations
    @Getter
    private byte[] clientSalt;
    private byte[] key;

    // Holder for the server keypair
    private final EncryptionKeyFactory keyFactory;

    /**
     * Create a new EncryptionHandler for the client
     *
     * @param keyFactory The keyFactory which created the server keypair
     */
    public EncryptionHandler( EncryptionKeyFactory keyFactory ) {
        this.keyFactory = keyFactory;
    }

    /**
     * Supplies the needed public key of the login to create the right encryption pairs
     *
     * @param key The key which should be used to encrypt traffic
     */
    public void supplyClientKey( ECPublicKey key ) {
        this.clientPublicKey = key;
    }

    /**
     * Sets up everything required to begin encrypting network data sent to or received from the client.
     *
     * @return Whether or not the setup completed successfully
     */
    public boolean beginClientsideEncryption() {
        if ( this.clientEncryptor != null && this.clientDecryptor != null ) {
            // Already initialized:
            LOGGER.debug( "Already initialized" );
            return true;
        }

        // Generate a random salt:
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance( "SHA1PRNG" );
        } catch ( NoSuchAlgorithmException e ) {
            LOGGER.warn( "Could not get secure random instance", e );
            return false;
        }

        this.clientSalt = secureRandom.generateSeed( 16 );

        // Generate shared secret from ECDH keys:
        byte[] secret = this.generateECDHSecret( this.keyFactory.getKeyPair().getPrivate(), this.clientPublicKey );
        if ( secret == null ) {
            return false;
        }

        // Derive key as salted SHA-256 hash digest:
        this.key = this.hashSHA256( this.clientSalt, secret );
        byte[] iv = this.takeBytesFromArray( this.key, 0, 16 );

        // Initialize BlockCiphers:
        this.clientEncryptor = this.createCipher( true, this.key, iv );
        this.clientDecryptor = this.createCipher( false, this.key, iv );
        return true;
    }

    /**
     * Decrypt data from the clients
     *
     * @param input RAW packet data from RakNet
     * @return Either null when the data was corrupted or the decrypted data
     */
    public byte[] decryptInputFromClient( byte[] input ) {
        byte[] output = this.processCipher( this.clientDecryptor, input );
        if ( output == null ) {
            return null;
        }

        byte[] outputChunked = new byte[input.length - 8];

        System.arraycopy( output, 0, outputChunked, 0, outputChunked.length );

        /*byte[] hashBytes = calcHash( outputChunked, this.receiveCounter );
        for ( int i = output.length - 8; i < output.length; i++ ) {
            if ( hashBytes[i - ( output.length - 8 )] != output[i] ) {
                return null;
            }
        }*/

        return outputChunked;
    }

    /**
     * Encrypt data for the client
     *
     * @param input zlib compressed data
     * @return data ready to be sent directly to the client
     */
    public byte[] encryptInputForClient( byte[] input ) {
        byte[] hashBytes = calcHash( input, this.sendingCounter );
        byte[] finalInput = new byte[hashBytes.length + input.length];

        System.arraycopy( input, 0, finalInput, 0, input.length );
        System.arraycopy( hashBytes, 0, finalInput, input.length, 8 );

        return this.processCipher( this.clientEncryptor, finalInput );
    }


    /**
     * Get the servers public key
     *
     * @return BASE64 encoded public key
     */
    public String getServerPublic() {
        return Base64.getEncoder().encodeToString( this.keyFactory.getKeyPair().getPublic().getEncoded() );
    }

    /**
     * Return the private key of the server. This should only be used to sign JWT content
     *
     * @return the private key
     */
    public Key getServerPrivate() {
        return this.keyFactory.getKeyPair().getPrivate();
    }

    private MessageDigest getSHA256() {
        MessageDigest digest = SHA256_DIGEST.get();
        if ( digest != null ) {
            digest.reset();
            return digest;
        }

        try {
            digest = MessageDigest.getInstance( "SHA-256" );
            SHA256_DIGEST.set( digest );
            return digest;
        } catch ( NoSuchAlgorithmException e ) {
            LOGGER.error( "Could not create SHA256 digest" );
        }

        return null;
    }

    private byte[] calcHash( byte[] input, AtomicLong counter ) {
        try {
            MessageDigest digest = getSHA256();
            if ( digest == null ) {
                return new byte[8];
            }

            byte[] result = new byte[digest.getDigestLength()];
            digest.update( ByteBuffer.allocate( 8 ).order( ByteOrder.LITTLE_ENDIAN ).putLong( counter.getAndIncrement() ).array(), 0, 8 );
            digest.update( input, 0, input.length );
            digest.update( this.key, 0, this.key.length );
            digest.digest( result, 0, result.length );
            return Arrays.copyOf( result, 8 );
        } catch ( DigestException e ) {
            LOGGER.error( "Could not create SHA256 hash", e );
        }

        return new byte[8];
    }

    private byte[] processCipher( Cipher cipher, byte[] input ) {
        byte[] output = new byte[cipher.getOutputSize( input.length )];

        try {
            int cursor = cipher.update( input, 0, input.length, output, 0 );
            // cursor += cipher.doFinal( output, cursor );
            if ( cursor != output.length ) {
                throw new ShortBufferException( "Output size did not match cursor" );
            }
        } catch ( ShortBufferException e ) {
            LOGGER.error( "Could not encrypt/decrypt to/from cipher-text", e );
            return null;
        }

        return output;
    }

    // ========================================== Utility Methods

    private byte[] generateECDHSecret( PrivateKey privateKey, PublicKey publicKey ) {
        try {
            KeyAgreement ka = KeyAgreement.getInstance( "ECDH" );
            ka.init( privateKey );
            ka.doPhase( publicKey, true );
            return ka.generateSecret();
        } catch ( NoSuchAlgorithmException | InvalidKeyException e ) {
            LOGGER.error( "Failed to generate Elliptic-Curve-Diffie-Hellman Shared Secret for clientside encryption", e );
            return null;
        }
    }

    private byte[] takeBytesFromArray( byte[] buffer, int offset, int length ) {
        byte[] result = new byte[length];
        System.arraycopy( buffer, offset, result, 0, length );
        return result;
    }

    private byte[] hashSHA256( byte[]... message ) {
        try {
            MessageDigest digest = getSHA256();
            if ( digest == null ) {
                return null;
            }

            byte[] result = new byte[digest.getDigestLength()];

            for ( byte[] bytes : message ) {
                digest.update( bytes, 0, bytes.length );
            }

            digest.digest( result, 0, result.length );
            return result;
        } catch ( DigestException e ) {
            LOGGER.error( "Could not create SHA256 hash", e );
        }

        return new byte[256];
    }

    private Cipher createCipher( boolean encryptor, byte[] key, byte[] iv ) {
        SecretKey secretKey = new SecretKeySpec( key, "AES" );
        IvParameterSpec ivParameterSpec = new IvParameterSpec( iv );

        try {
            Cipher jdkCipher = Cipher.getInstance( "AES/CFB8/NoPadding" );
            jdkCipher.init( encryptor ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, ivParameterSpec );
            return jdkCipher;
        } catch ( NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e ) {
            LOGGER.error( "Could not create cipher", e );
        }

        return null;
    }

}

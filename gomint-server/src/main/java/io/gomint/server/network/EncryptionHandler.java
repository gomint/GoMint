/*
 *  Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyAgreement;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

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

    // Holder for the server keypair
    private final EncryptionKeyFactory keyFactory;

    // Client Side:
    private ECPublicKey clientPublicKey;

    // Data for packet and checksum calculations
    private byte[] clientSalt;
    private byte[] key;
    private byte[] iv;

    // Server side
    private PublicKey serverPublicKey;
    private byte[] serverKey;
    private byte[] serverIv;

    /**
     * Create a new EncryptionHandler for the client
     *
     * @param keyFactory The keyFactory which created the server keypair
     */
    public EncryptionHandler( EncryptionKeyFactory keyFactory ) {
        this.keyFactory = keyFactory;
    }

    public byte[] clientSalt() {
        return this.clientSalt;
    }

    public byte[] key() {
        return this.key;
    }

    public byte[] iv() {
        return this.iv;
    }

    public byte[] serverKey() {
        return this.serverKey;
    }

    public byte[] serverIv() {
        return this.serverIv;
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
        if ( this.key != null && this.clientSalt != null ) {
            // Already initialized:
            LOGGER.debug( "Already initialized" );
            return true;
        }

        // Generate a random salt:
        this.clientSalt = new byte[16];
        ThreadLocalRandom.current().nextBytes( this.clientSalt );

        // Generate shared secret from ECDH keys:
        byte[] secret = this.generateECDHSecret( this.keyFactory.keyPair().getPrivate(), this.clientPublicKey );
        if ( secret == null ) {
            return false;
        }

        // Derive key as salted SHA-256 hash digest:
        try {
            this.key = this.hashSHA256( this.clientSalt, secret );
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("no sha-265 found", e);
            return false;
        }

        this.iv = this.takeBytesFromArray( this.key, 0, 16 );

        // Initialize BlockCiphers:
        return true;
    }

    /**
     * Sets the server's public ECDH key which is required for decoding packets received from the proxied server and
     * encoding packets to be sent to the proxied server.
     *
     * @param key the key from the server
     */
    public void serverPublicKey(PublicKey key ) {
        this.serverPublicKey = key;
    }

    /**
     * Sets up everything required for encrypting and decrypting networking data received from the proxied server.
     *
     * @param salt The salt to prepend in front of the ECDH derived shared secret before hashing it (sent to us from the
     *             proxied server in a 0x03 packet)
     */
    public boolean beginServersideEncryption( byte[] salt ) {
        if ( this.serverKey != null && this.serverIv != null ) {
            // Already initialized:
            LOGGER.debug( "Already initialized" );
            return true;
        }

        // Generate shared secret from ECDH keys:
        byte[] secret = this.generateECDHSecret( this.keyFactory.keyPair().getPrivate(), this.serverPublicKey );
        if ( secret == null ) {
            return false;
        }

        // Derive key as salted SHA-256 hash digest:
        try {
            this.serverKey = this.hashSHA256( salt, secret );
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("no sha-265 found", e);
            return false;
        }

        this.serverIv = this.takeBytesFromArray( this.serverKey, 0, 16 );

        return true;
    }

    /**
     * Get the servers public key
     *
     * @return BASE64 encoded public key
     */
    public String serverPublic() {
        return Base64.getEncoder().encodeToString( this.keyFactory.keyPair().getPublic().getEncoded() );
    }

    /**
     * Return the private key of the server. This should only be used to sign JWT content
     *
     * @return the private key
     */
    public Key serverPrivate() {
        return this.keyFactory.keyPair().getPrivate();
    }

    private MessageDigest getSHA256() throws NoSuchAlgorithmException {
        MessageDigest digest = SHA256_DIGEST.get();
        if (digest != null) {
            digest.reset();
            return digest;
        }

        digest = MessageDigest.getInstance("SHA-256");
        SHA256_DIGEST.set(digest);
        return digest;
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

    private byte[] hashSHA256( byte[]... message ) throws NoSuchAlgorithmException {
        MessageDigest digest = getSHA256();

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        for ( byte[] bytes : message ) {
            buf.writeBytes( bytes );
        }

        digest.update( buf.nioBuffer() );
        buf.release();
        return digest.digest();
    }

}

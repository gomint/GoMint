/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator.client;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyAgreement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
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

    private static KeyFactory ECDH_KEY_FACTORY;
    public static KeyPair PROXY_KEY_PAIR;

    static {
        // Initialize KeyFactory:
        try {
            ECDH_KEY_FACTORY = KeyFactory.getInstance( "ECDH", "BC" );
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace();
            System.err.println( "Could not find ECDH Key Factory - please ensure that you have installed the latest version of BouncyCastle" );
            System.exit( -1 );
        } catch ( NoSuchProviderException e ) {
            e.printStackTrace();
            System.err.println( "Could not find BouncyCastle Key Provider - please ensure that you have installed BouncyCastle properly" );
            System.exit( -1 );
        }

        generateEncryptionKeys();
    }

    public static ECPublicKey createPublicKey( String base64 ) {
        try {
            return (ECPublicKey) ECDH_KEY_FACTORY.generatePublic( new X509EncodedKeySpec( Base64.getDecoder()
                    .decode( base64 ) ) );
        } catch ( InvalidKeySpecException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a new ECDSA Key Pair using the SEC curve secp384r1 provided by BouncyCastle. This must be invoked
     * before attempting to build a shared secret for the client or the backend server.
     */
    public static void generateEncryptionKeys() {
        // Setup KeyPairGenerator:
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance( "EC", "BC" );
            generator.initialize( 384 );
        } catch ( NoSuchAlgorithmException | NoSuchProviderException e ) {
            System.err.println( "It seems you have not installed a recent version of BouncyCastle; please ensure that your version supports EC Key-Pair-Generation using the secp384r1 curve" );
            System.exit( -1 );
            return;
        }

        // Generate the keypair:
        PROXY_KEY_PAIR = generator.generateKeyPair();

        try ( BufferedWriter writer = new BufferedWriter( new FileWriter( "server.public.key" ) ) ) {
            writer.write( Base64.getEncoder().encodeToString( PROXY_KEY_PAIR.getPublic().getEncoded() ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        try ( BufferedWriter writer = new BufferedWriter( new FileWriter( "server.private.key" ) ) ) {
            writer.write( Base64.getEncoder().encodeToString( PROXY_KEY_PAIR.getPrivate().getEncoded() ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( EncryptionHandler.class );

    // Packet counters
    private AtomicLong sendingCounter = new AtomicLong( 0 );
    private AtomicLong receiveCounter = new AtomicLong( 0 );

    // Client Side:
    private ECPublicKey clientPublicKey;
    private BufferedBlockCipher clientEncryptor;
    private BufferedBlockCipher clientDecryptor;

    // Data for packet and checksum calculations
    @Getter
    @Setter
    private byte[] clientSalt;
    private byte[] key;

    // Server side
    private ECPublicKey serverPublicKey;
    private BufferedBlockCipher serverEncryptor;
    private BufferedBlockCipher serverDecryptor;
    private AtomicLong serverSendCounter = new AtomicLong( 0 );
    private AtomicLong serverReceiveCounter = new AtomicLong( 0 );
    private byte[] serverKey;

    /**
     * Supplies the needed public key of the login to create the right encryption pairs
     *
     * @param key The key which should be used to encrypt traffic
     */
    public void supplyClientKey( ECPublicKey key ) {
        this.clientPublicKey = key;
    }

    /**
     * Sets the server's public ECDH key which is required for decoding packets received from the proxied server and
     * encoding packets to be sent to the proxied server.
     *
     * @param base64 The base64 string containing the encoded public key data
     */
    public void setServerPublicKey( String base64 ) {
        this.serverPublicKey = createPublicKey( base64 );
    }

    /**
     * Sets up everything required for encrypting and decrypting networking data received from the proxied server.
     *
     * @param salt The salt to prepend in front of the ECDH derived shared secret before hashing it (sent to us from the
     *             proxied server in a 0x03 packet)
     */
    public boolean beginServersideEncryption( byte[] salt ) {
        if ( this.isEncryptionFromServerEnabled() ) {
            // Already initialized:
            return true;
        }

        // Generate shared secret from ECDH keys:
        byte[] secret = this.generateECDHSecret( PROXY_KEY_PAIR.getPrivate(), this.serverPublicKey );
        if ( secret == null ) {
            return false;
        }

        // Derive key as salted SHA-256 hash digest:
        this.serverKey = this.hashSHA256( salt, secret );
        byte[] iv = this.takeBytesFromArray( this.serverKey, 0, 16 );

        // Initialize BlockCiphers:
        this.serverEncryptor = this.createCipher( true, this.serverKey, iv );
        this.serverDecryptor = this.createCipher( false, this.serverKey, iv );
        return true;
    }

    public boolean isEncryptionFromServerEnabled() {
        return ( this.serverEncryptor != null && this.serverDecryptor != null );
    }

    public byte[] decryptInputFromServer( byte[] input ) {
        byte[] output = this.processCipher( this.serverDecryptor, input );
        if ( output == null ) {
            return null;
        }

        byte[] outputChunked = new byte[input.length - 8];

        System.arraycopy( output, 0, outputChunked, 0, outputChunked.length );

        byte[] hashBytes = calcHash( outputChunked, this.serverKey, this.serverReceiveCounter );
        for ( int i = output.length - 8; i < output.length; i++ ) {
            if ( hashBytes[i - ( output.length - 8 )] != output[i] ) {
                return null;
            }
        }

        return outputChunked;
    }

    public byte[] encryptInputForServer( byte[] input ) {
        byte[] hashBytes = calcHash( input, this.serverKey, this.serverSendCounter );
        byte[] finalInput = new byte[hashBytes.length + input.length];

        System.arraycopy( input, 0, finalInput, 0, input.length );
        System.arraycopy( hashBytes, 0, finalInput, input.length, 8 );

        return this.processCipher( this.serverEncryptor, finalInput );
    }

    /**
     * Sets up everything required to begin encrypting network data sent to or received from the client.
     *
     * @return Whether or not the setup completed successfully
     */
    public boolean beginClientsideEncryption() {
        if ( this.clientEncryptor != null && this.clientDecryptor != null ) {
            // Already initialized:
            return true;
        }

        // Generate a random salt:
        SecureRandom secureRandom = new SecureRandom();
        this.clientSalt = secureRandom.generateSeed( 16 );

        // Generate shared secret from ECDH keys:
        byte[] secret = this.generateECDHSecret( PROXY_KEY_PAIR.getPrivate(), this.clientPublicKey );
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

        byte[] hashBytes = calcHash( outputChunked, this.key, this.receiveCounter );
        for ( int i = output.length - 8; i < output.length; i++ ) {
            if ( hashBytes[i - ( output.length - 8 )] != output[i] ) {
                return null;
            }
        }

        return outputChunked;
    }

    /**
     * Encrypt data for the client
     *
     * @param input zlib compressed data
     * @return data ready to be sent directly to the client
     */
    public byte[] encryptInputForClient( byte[] input ) {
        byte[] hashBytes = calcHash( input, this.key, this.sendingCounter );
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
        return Base64.getEncoder().encodeToString( PROXY_KEY_PAIR.getPublic().getEncoded() );
    }

    /**
     * Return the private key of the server. This should only be used to sign JWT content
     *
     * @return the private key
     */
    public Key getServerPrivate() {
        return PROXY_KEY_PAIR.getPrivate();
    }

    private byte[] calcHash( byte[] input, byte[] key, AtomicLong counter ) {
        SHA256Digest digest = new SHA256Digest();

        byte[] result = new byte[digest.getDigestSize()];
        digest.update( ByteBuffer.allocate( 8 ).order( ByteOrder.LITTLE_ENDIAN ).putLong( counter.getAndIncrement() ).array(), 0, 8 );
        digest.update( input, 0, input.length );
        digest.update( key, 0, key.length );
        digest.doFinal( result, 0 );

        return Arrays.copyOf( result, 8 );
    }

    private byte[] processCipher( BufferedBlockCipher cipher, byte[] input ) {
        byte[] output = new byte[cipher.getOutputSize( input.length )];
        int cursor = cipher.processBytes( input, 0, input.length, output, 0 );

        try {
            // cursor += cipher.doFinal( output, cursor );
            if ( cursor != output.length ) {
                throw new InvalidCipherTextException( "Output size did not match cursor" );
            }
        } catch ( InvalidCipherTextException e ) {
            LOGGER.error( "Could not encrypt/decrypt to/from cipher-text", e );
            return null;
        }

        return output;
    }

    // ========================================== Utility Methods

    private byte[] generateECDHSecret( PrivateKey privateKey, PublicKey publicKey ) {
        try {
            KeyAgreement ka = KeyAgreement.getInstance( "ECDH", "BC" );
            ka.init( privateKey );
            ka.doPhase( publicKey, true );
            return ka.generateSecret();
        } catch ( NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException e ) {
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
        SHA256Digest digest = new SHA256Digest();

        byte[] result = new byte[digest.getDigestSize()];
        for ( byte[] bytes : message ) {
            digest.update( bytes, 0, bytes.length );
        }

        digest.doFinal( result, 0 );

        return result;
    }

    private BufferedBlockCipher createCipher( boolean encryptor, byte[] key, byte[] iv ) {
        BufferedBlockCipher cipher = new BufferedBlockCipher( new CFBBlockCipher( new AESEngine(), 8 ) );
        cipher.init( encryptor, new ParametersWithIV( new KeyParameter( key ), iv ) );
        return cipher;
    }

}

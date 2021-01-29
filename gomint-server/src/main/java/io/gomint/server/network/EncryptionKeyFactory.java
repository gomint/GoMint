/*
 *  Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EncryptionKeyFactory {

    private PublicKey rootKey = null;
    private String rootKeyBase64;

    private KeyFactory keyFactory;
    private KeyPair keyPair;

    public EncryptionKeyFactory(String jwtRoot) {
        // Create the key factory
        try {
            this.keyFactory = KeyFactory.getInstance("EC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.err.println("Could not find ECDH Key Factory - please ensure that you have installed the latest version of BouncyCastle");
            System.exit(-1);
        }

        if (!jwtRoot.isEmpty()) {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(jwtRoot));

            // Unserialize the Mojang root key
            try {
                this.rootKey = this.keyFactory.generatePublic(keySpec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                System.err.println("Could not generated public key for trusted Mojang key; please report this error in the GoMint.io discord for further assistance");
                System.exit(-1);
            }

            this.rootKeyBase64 = jwtRoot;
        }

        // Setup KeyPairGenerator:
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("EC");
            generator.initialize(384);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("It seems you have not installed a recent version of BouncyCastle; please ensure that your version supports EC Key-Pair-Generation using the secp384r1 curve");
            System.exit(-1);
            return;
        }

        // Generate the keypair:
        this.keyPair = generator.generateKeyPair();
    }

    public KeyPair keyPair() {
        return this.keyPair;
    }

    public PublicKey createPublicKey(String base64) {
        try {
            return this.keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base64)));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String rootKeyBase64() {
        return this.rootKeyBase64;
    }

    public Key rootKey() {
        return this.rootKey;
    }

    public boolean isKeyGiven() {
        return this.rootKey != null;
    }

}

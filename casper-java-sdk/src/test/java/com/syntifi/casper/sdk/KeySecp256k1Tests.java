package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.syntifi.casper.sdk.key.KeySecp256k1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KeySecp256k1Tests {
    private static KeySecp256k1 key;

    @BeforeAll
    public static void setUp() {
        key = new KeySecp256k1();
    }

    @Test
    void derivePublicKeySecp256k1() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        key.readPublicKey("src/test/resources/secp256k1-keys/public_key.pem");
        key.readPrivateKey("src/test/resources/secp256k1-keys/secret_key.pem");
        assertEquals(key.getPublicKey(), key.derivePublicKey());
    } 
   
}

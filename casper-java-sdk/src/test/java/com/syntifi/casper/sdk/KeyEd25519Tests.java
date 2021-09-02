package com.syntifi.casper.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.syntifi.casper.sdk.key.KeyEd25519;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;

public class KeyEd25519Tests {
    private static KeyEd25519 key;

    @BeforeAll
    public static void setUp() {
        key = new KeyEd25519();
    }

    @Test
    void readPublicKeyEd25519FromPemAndGetHexKey() throws IOException {
        key.readPublicKey("src/test/resources/ed25519-keys/public_key.pem");
        assertEquals(32, key.getPublicKey().getEncoded().length);
        FileInputStream fstream = new FileInputStream("src/test/resources/ed25519-keys/public_key_hex");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(fstream))){
            assertEquals(br.readLine(), key.getHexPublicKey());
        }
    } 

    @Test
    void derivePublicKeyEd25519() throws IOException {
        key.readPrivateKey("src/test/resources/ed25519-keys/secret_key.pem");
        key.readPublicKey("src/test/resources/ed25519-keys/public_key.pem");
        assertEquals(Hex.toHexString(key.getPublicKey().getEncoded()), 
                    Hex.toHexString(key.derivePublicKey().getEncoded()));
    } 

    @Test
    void writePrivateKeyEd25519() throws IOException {
        key.readPublicKey("src/test/resources/ed25519-keys/public_key.pem");
        key.readPrivateKey("src/test/resources/ed25519-keys/secret_key.pem");
        key.writePrivateKey("src/test/resources/ed25519-keys/secret_key_write.pem");
        assertTrue(FileUtils.contentEquals(new File("src/test/resources/ed25519-keys/secret_key.pem"), 
            new File("src/test/resources/ed25519-keys/secret_key_write.pem")));
    }

    @Test
    void writePublicKeyEd25519() throws IOException {
        key.readPublicKey("src/test/resources/ed25519-keys/public_key.pem");
        key.readPrivateKey("src/test/resources/ed25519-keys/secret_key.pem");
        key.writePublicKey("src/test/resources/ed25519-keys/public_key_write.pem");
        assertTrue(FileUtils.contentEquals(new File("src/test/resources/ed25519-keys/public_key.pem"), 
            new File("src/test/resources/ed25519-keys/public_key_write.pem")));
    }
}


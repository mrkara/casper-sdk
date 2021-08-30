package com.syntifi.casper.sdk.key;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link KeyReader}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class KeyReaderTests {
    Logger logger = LoggerFactory.getLogger(KeyReaderTests.class);

    private final String PUBLIC_KEY_PATH = "public_key.pem";
    private final String PRIVATE_KEY_PATH = "secret_key.pem";

    /**
     * Test loading a casper private key
     * 
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * 
     * @throws Throwable
     */
    @Test
    void loadPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        var privateKey = KeyReader.getPrivateKey(getFile(PRIVATE_KEY_PATH));

        assertNotNull(privateKey);
    }

    /**
     * Test loading a casper public key
     * 
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * 
     * @throws Exception
     * 
     * @throws Throwable
     */
    @Test
    void loadPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        var privateKey = KeyReader.getPublicKey(getFile(PUBLIC_KEY_PATH));

        assertNotNull(privateKey);
    }

    private String getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            var path = new File(resource.getFile()).getAbsolutePath();
            return path;
        }
    }
}

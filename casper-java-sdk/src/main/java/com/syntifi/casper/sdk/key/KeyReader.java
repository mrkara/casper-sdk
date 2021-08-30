package com.syntifi.casper.sdk.key;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public final class KeyReader {

    private static Logger logger = LoggerFactory.getLogger(KeyReader.class);

    private KeyReader() {
    }

    public static PrivateKey getPrivateKey(String filename) throws IOException {
        String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());

        Optional<String> keyToParse = Arrays.asList(key.split("\r?\n")).stream().filter(x -> !x.startsWith("---"))
                .findFirst();

        if (keyToParse.isPresent()) {
            String privateKeyPEM = keyToParse.get().trim();

            logger.info(privateKeyPEM);

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);

            EdDSAParameterSpec ed25519Spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
            EdDSAPrivateKeySpec privateKeyRebuildSpec = new EdDSAPrivateKeySpec(Arrays.copyOfRange(keyBytes, 0, 32),
                    ed25519Spec);
            return new EdDSAPrivateKey(privateKeyRebuildSpec);
        } else {
            throw new RuntimeException("No key found in file");
        }
        // PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        // KeyFactory kf = KeyFactory.getInstance("Ed25519");
        // return kf.generatePrivate(spec);
    }

    public static PublicKey getPublicKey(String filename) throws IOException {
        String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());

        Optional<String> keyToParse = Arrays.asList(key.split("\r?\n")).stream().filter(x -> !x.startsWith("---"))
                .findFirst();

        if (keyToParse.isPresent()) {
            String publicKeyPEM = keyToParse.get().trim();

            logger.info(publicKeyPEM);

            byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);

            EdDSAParameterSpec ed25519Spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
            EdDSAPublicKeySpec publicKeyRebuildSpec = new EdDSAPublicKeySpec(Arrays.copyOfRange(keyBytes, 0, 32), ed25519Spec);
            return new EdDSAPublicKey(publicKeyRebuildSpec);
        } else {
            throw new RuntimeException("No key found in file");
        }

        // X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        // KeyFactory kf = KeyFactory.getInstance("Ed25519");
        // return kf.generatePublic(spec);
    }

    public static KeyPair generateEcEd25519KeyPair() {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = new KeyPairGenerator();
        keyPairGenerator.initialize(256, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] signEcEd25519PrivateKey(PrivateKey privateKey, byte[] messageByte)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        Signature signature = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
        signature.initSign(privateKey);
        signature.update(messageByte);
        return signature.sign();
    }

    public static Boolean verifyEcEd25519PublicKey(PublicKey publicKey, byte[] messageByte, byte[] signatureByte)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        Signature signature = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
        signature.initVerify(publicKey);
        signature.update(messageByte);
        return signature.verify(signatureByte);
    }

    private static void saveEd25519_32byte_PrivateKeyAsBytearray(PrivateKey key, String filenameString)
            throws IOException, InvalidKeySpecException {
        PKCS8EncodedKeySpec priEncoded = new PKCS8EncodedKeySpec(key.getEncoded());
        EdDSAPrivateKey eddsaPrivateKey = new EdDSAPrivateKey(priEncoded);
        byte[] priSeedByte = eddsaPrivateKey.getSeed();
        try (FileOutputStream fos = new FileOutputStream(filenameString)) {
            fos.write(priSeedByte);
        }
    }

    private static void saveEd25519_32byte_PublicKeyAsBytearray(PublicKey key, String filenameString)
            throws IOException, InvalidKeySpecException {
        X509EncodedKeySpec pubEncoded = new X509EncodedKeySpec(key.getEncoded());
        EdDSAPublicKey eddsaPublicKey = new EdDSAPublicKey(pubEncoded);
        byte[] pubSeedByte = eddsaPublicKey.getA().toByteArray();
        try (FileOutputStream fos = new FileOutputStream(filenameString)) {
            fos.write(pubSeedByte);
        }
    }

    private static PrivateKey loadEcEd25519_32byte_PrivateKeyAsBytearray(String filenameString)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        File filenameKeyString = new File(filenameString);
        try (FileInputStream fis = new FileInputStream(filenameKeyString)) {
            byte[] encodedPrivateKey = new byte[(int) filenameKeyString.length()];
            if (fis.read(encodedPrivateKey) == -1) {
                // TODO: Improve exception
                throw new RuntimeException("no data");
            }
            EdDSAParameterSpec ed25519Spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
            EdDSAPrivateKeySpec privateKeyRebuildSpec = new EdDSAPrivateKeySpec(encodedPrivateKey, ed25519Spec);
            return new EdDSAPrivateKey(privateKeyRebuildSpec);
        }
    }

    private static PublicKey loadEcEd25519_32byte_PublicKeyAsBytearray(String filenameString)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        File filenameKeyString = new File(filenameString);
        try (FileInputStream fis = new FileInputStream(filenameKeyString)) {
            byte[] encodedPublicKey = new byte[(int) filenameKeyString.length()];
            if (fis.read(encodedPublicKey) == -1) {
                // TODO: Improve exception
                throw new RuntimeException("no data");
            }
            EdDSAParameterSpec ed25519Spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
            EdDSAPublicKeySpec publicKeyRebuildSpec = new EdDSAPublicKeySpec(encodedPublicKey, ed25519Spec);
            return new EdDSAPublicKey(publicKeyRebuildSpec);
        }
    }

    public static byte[] calcSHA256BufferedFile(String filenameString) throws IOException, NoSuchAlgorithmException {
        byte[] buffer = new byte[8192];
        int count;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filenameString))) {
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            return digest.digest();
        }
    }
}

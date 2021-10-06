package com.syntifi.casper.sdk.model.key;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.syntifi.casper.sdk.jackson.deserializer.PublicKeyDeserializer;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;

import lombok.NoArgsConstructor;

/**
 * Hex-encoded cryptographic public key, including the algorithm tag prefix.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonDeserialize(using = PublicKeyDeserializer.class)
@NoArgsConstructor
public class PublicKey extends AbstractAlgoTaggedHex {

    public static PublicKey fromTaggedHexString(String hex) throws NoSuchAlgorithmException{
        PublicKey object = new PublicKey();
        byte[] bytes = StringByteHelper.hexStringToByteArray(hex);
        object.setAlgorithm(Algorithm.getByTag(bytes[0]));
        object.setKey(Arrays.copyOfRange(bytes, 1, bytes.length));
        return object;
    }

    @JsonCreator
    public void createPublicKey(String key) throws NoSuchAlgorithmException {
        PublicKey obj = PublicKey.fromTaggedHexString(key);
        this.setAlgorithm(obj.getAlgorithm());
        this.setKey(obj.getKey());
    }

}

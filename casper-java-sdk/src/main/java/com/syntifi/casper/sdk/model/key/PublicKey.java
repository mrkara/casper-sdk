package com.syntifi.casper.sdk.model.key;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syntifi.casper.sdk.jackson.deserializer.PublicKeyDeserializer;
import com.syntifi.casper.sdk.jackson.serializer.AlgoTaggedHexSerializer;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;

import lombok.NoArgsConstructor;

/**
 * Hex-encoded cryptographic public key, including the algorithm tag prefix.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonSerialize(using = AlgoTaggedHexSerializer.class)
@JsonDeserialize(using = PublicKeyDeserializer.class)
@NoArgsConstructor
public class PublicKey extends AbstractAlgoTaggedHex {

    @JsonCreator
    public static PublicKey fromTaggedHexString(String hex) throws NoSuchAlgorithmException{
        PublicKey object = new PublicKey();
        byte[] bytes = StringByteHelper.hexStringToByteArray(hex);
        object.setAlgorithm(Algorithm.getByTag(bytes[0]));
        object.setKey(Arrays.copyOfRange(bytes, 1, bytes.length));
        return object;
    }

    public String toTaggedHexString(){
        byte[] algo = new byte[1];
        algo[0] = this.getAlgorithm().getTag();
        return StringByteHelper.convertBytesToHex(algo) + 
            StringByteHelper.convertBytesToHex(this.getKey()); 
    }

}

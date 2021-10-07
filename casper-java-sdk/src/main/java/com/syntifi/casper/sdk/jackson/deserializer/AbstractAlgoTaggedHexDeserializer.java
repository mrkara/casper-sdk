package com.syntifi.casper.sdk.jackson.deserializer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.syntifi.casper.sdk.exception.DeserializationException;
import com.syntifi.casper.sdk.exception.InvalidByteStringException;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;
import com.syntifi.casper.sdk.model.key.AbstractAlgoTaggedHex;
import com.syntifi.casper.sdk.model.key.Algorithm;

/**
 * Customize the mapping of Casper's Hex String preceeded by the crypto algorithm 
 * tag such as PublicKey/Signature
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public abstract class AbstractAlgoTaggedHexDeserializer<T extends AbstractAlgoTaggedHex> extends JsonDeserializer<T> {

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        T object = this.getInstanceOf();

        try {
            byte[] bytes = StringByteHelper.hexStringToByteArray(node.asText());
            object.setAlgorithm(Algorithm.getByTag(bytes[0]));
            object.setKey(Arrays.copyOfRange(bytes, 1, bytes.length));
        } catch (NoSuchAlgorithmException | InvalidByteStringException e) {
            throw new DeserializationException("Problem deserializing Algorithm tagged hexa string", e);
        }

        return object;
    }

    protected abstract T getInstanceOf();
}

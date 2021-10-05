package com.syntifi.casper.sdk.jackson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.syntifi.casper.sdk.model.key.AbstractAlgoTaggedHex;

import org.apache.commons.codec.binary.Hex;

/**
 * Customize the mapping of Casper's Hex String preceeded by the crypto algorithm 
 * tag such as PublicKey/Signature
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class AlgoTaggedHexSerializer extends JsonSerializer<AbstractAlgoTaggedHex> {
    
    @Override
    public void serialize(AbstractAlgoTaggedHex key, JsonGenerator jgen, SerializerProvider provider) 
        throws IOException{
        jgen.writeString(new String(Hex.encodeHex(new byte[] {key.getAlgorithm().getTag()}, true)) + 
                        new String(Hex.encodeHex(key.getKey(), true)));
    }
}
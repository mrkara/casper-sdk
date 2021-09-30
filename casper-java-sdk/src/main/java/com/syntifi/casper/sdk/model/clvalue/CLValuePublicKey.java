package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeBasic;
import com.syntifi.casper.sdk.model.key.PublicKey;

import org.apache.commons.codec.DecoderException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper PublicKey CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CLValuePublicKey extends CLValue<PublicKey, CLTypeBasic> {
    @JsonProperty("cl_type")
    @JsonUnwrapped
    private CLTypeBasic clType = new CLTypeBasic(CLType.PUBLIC_KEY);

    public CLValuePublicKey(PublicKey value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        clve.writePublicKey(this);
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        try {
            clvd.readPublicKey(this);
        } catch (DecoderException | NoSuchAlgorithmException e) {
            throw new CLValueDecodeException("Error decoding CLValuePublicKey", e);
        }
    }

    @Override
    protected void setChildTypes() {
        // TODO Auto-generated method stub

    }
}

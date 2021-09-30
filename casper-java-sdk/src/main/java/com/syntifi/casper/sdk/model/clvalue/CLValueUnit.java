package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeBasic;
import com.syntifi.casper.sdk.model.storedvalue.clvalue.AbstractCLValue;

import lombok.Data;

/**
 * Casper Unit CLValue implementation
 * 
 * Unit is singleton value without additional semantics and serializes to an
 * empty byte array.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
@Data
public class CLValueUnit extends CLValue<Object, CLTypeBasic> {
    private static final String UNITY_EMPTY_VALUE = "";
    @JsonProperty("cl_type")
    @JsonUnwrapped
    private CLTypeBasic clType;

    // @Override
    // public void setClType(CLType value) {
    //     this.clType = (CLTypeBasic) value;
    // }

    public CLValueUnit() {
        this.setValue(UNITY_EMPTY_VALUE);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        setBytes(UNITY_EMPTY_VALUE);
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        setBytes(UNITY_EMPTY_VALUE);
    }
}
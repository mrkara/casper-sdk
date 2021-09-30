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
 * Casper U32 CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
@Data
public class CLValueU32 extends CLValue<Long, CLTypeBasic> {
    @JsonProperty("cl_type")
    @JsonUnwrapped
    private CLTypeBasic clType;

    // @Override
    // public void setClType(CLType value) {
    //     this.clType = (CLTypeBasic) value;
    // }

    public CLValueU32(Long value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        clve.writeU32(this);
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        clvd.readU32(this);
    }
}

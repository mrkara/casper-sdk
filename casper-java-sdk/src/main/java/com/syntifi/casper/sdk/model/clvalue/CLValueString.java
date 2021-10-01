package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeString;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper String CLValue implementation
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
public class CLValueString extends CLValue<String, CLTypeString> {
    @JsonProperty("cl_type")
    @JsonUnwrapped
    private CLTypeString clType = new CLTypeString();

    public CLValueString(String value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        clve.writeString(this);
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        clvd.readString(this);
    }
}

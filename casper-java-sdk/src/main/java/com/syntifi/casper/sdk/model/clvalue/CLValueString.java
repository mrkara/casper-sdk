package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeBasic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CLValueString extends CLValue<String, CLTypeBasic> {
    @JsonProperty("cl_type")
    @JsonUnwrapped
    private CLTypeBasic clType = new CLTypeBasic(CLType.STRING);

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

    @Override
    protected void setChildTypes() {
        // TODO Auto-generated method stub

    }
}

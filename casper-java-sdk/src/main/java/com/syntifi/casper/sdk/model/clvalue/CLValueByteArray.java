package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeByteArray;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true) // TODO: Override and use Arrays.equal
@NoArgsConstructor
public class CLValueByteArray extends CLValue<byte[], CLTypeByteArray> {
    @JsonProperty("cl_type")
    private CLTypeByteArray clType;

    public CLValueByteArray(byte[] value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        clve.writeByteArray(this);
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        clvd.readByteArray(this, this.getClType().getLength());
    }

    @Override
    protected void setChildTypes() {
        // TODO Auto-generated method stub

    }
}

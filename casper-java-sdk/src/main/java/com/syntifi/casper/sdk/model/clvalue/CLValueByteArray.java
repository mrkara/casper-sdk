package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeByteArray;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CLValueByteArray extends CLValue<byte[], CLTypeByteArray> {
    @JsonProperty("cl_type")
    private CLTypeByteArray clType;

    // @Override
    // public void setClType(CLType value) {
    // this.clType = (CLTypeByteArray)value;
    // }

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
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CLValueByteArray))
            return false;
        final CLValueByteArray other = (CLValueByteArray) o;
        if (!other.canEqual(this))
            return false;
        if (this.getClType() == null ? other.getClType() != null : !this.getClType().equals(other.getClType()))
            return false;
        if (this.getBytes() == null ? other.getBytes() != null : !this.getBytes().equals(other.getBytes()))
            return false;
        if (this.getParsed() == null ? other.getParsed() != null : !this.getParsed().equals(other.getParsed()))
            return false;
        if (this.getValue() == null ? other.getValue() != null : !Arrays.equals(this.getValue(), other.getValue()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int result = 1;
        return result;
    }
}

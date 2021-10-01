package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeByteArray;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper ByteArray CLValue implementation
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @since 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class CLValueByteArray extends CLValue<byte[], CLTypeByteArray> {
    @JsonProperty("cl_type")
    private CLTypeByteArray clType = new CLTypeByteArray();

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
        if (!other.canEqual((Object) this))
            return false;
        final Object this$bytes = this.getBytes();
        final Object other$bytes = other.getBytes();
        if (this$bytes == null ? other$bytes != null : !this$bytes.equals(other$bytes))
            return false;
        final byte[] this$value = this.getValue();
        final byte[] other$value = other.getValue();
        if (this$value == null ? other$value != null : !Arrays.equals(this$value, other$value))
            return false;
        final Object this$clType = this.getClType();
        final Object other$clType = other.getClType();
        if (this$clType == null ? other$clType != null : !this$clType.equals(other$clType))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CLValueByteArray;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $clType = this.getClType();
        result = result * PRIME + ($clType == null ? 43 : $clType.hashCode());
        return result;
    }
}

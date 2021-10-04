package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeURef;
import com.syntifi.casper.sdk.model.uref.URef;
import com.syntifi.casper.sdk.model.uref.URefAccessRight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Casper Boolean CLURef implementation URef is a tuple that contains the
 * address of the URef and the access rights to that URef. The serialized
 * representation of the URef is 33 bytes long. The first 32 bytes are the byte
 * representation of the URef address, and the last byte contains the bits
 * corresponding to the access rights of the URef.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLValue
 * @see URef
 * @since 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class CLValueURef extends CLValue<URef, CLTypeURef> {
    private CLTypeURef clType = new CLTypeURef();

    @JsonSetter("cl_type")
    public void setJsonClType(CLTypeURef clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    public String getJsonClType() {
        return this.getClType().getTypeName();
    }

    public CLValueURef(URef value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        URef uref = this.getValue();
        byte[] urefByte = new byte[uref.getAddress().length + 1];
        System.arraycopy(uref.getAddress(), 0, urefByte, 0, uref.getAddress().length);
        urefByte[32] = uref.getAccessRight().serializationTag; 
        setBytes(StringByteHelper.convertBytesToHex(urefByte));
    }

    @Override
    public void decode(CLValueDecoder clvd) throws IOException, CLValueDecodeException, DynamicInstanceException {
        URef uref = new URef();
        CLValueByteArray clValueByteArray = new CLValueByteArray(new byte[] {});
        clvd.readByteArray(clValueByteArray, 32);
        uref.setAddress(clValueByteArray.getValue());
        CLValueU8 serializationTag = new CLValueU8((byte) 0);
        clvd.readU8(serializationTag);
        uref.setAccessRight(URefAccessRight.getTypeBySerializationTag(serializationTag.getValue()));
        setValue(uref);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CLValueURef))
            return false;
        final CLValueURef other = (CLValueURef) o;
        if (!other.canEqual((Object) this))
            return false;
        final Object this$bytes = this.getBytes();
        final Object other$bytes = other.getBytes();
        if (this$bytes == null ? other$bytes != null : !this$bytes.equals(other$bytes))
            return false;
        final URef this$value = this.getValue();
        final URef other$value = other.getValue();
        if (this$value == null ? other$value != null : !(this$value.equals(other$value)))
            return false;
        final Object this$clType = this.getClType();
        final Object other$clType = other.getClType();
        if (this$clType == null ? other$clType != null : !this$clType.equals(other$clType))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CLValueURef;
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

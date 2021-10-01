package com.syntifi.casper.sdk.model.clvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.exception.CLValueDecodeException;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.syntifi.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.syntifi.casper.sdk.model.clvalue.type.CLTypeURef;
import com.syntifi.casper.sdk.model.uref.URef;
import com.syntifi.casper.sdk.model.uref.URefAccessRight;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CLValueURef extends CLValue<URef, CLTypeURef> {
    @JsonProperty("cl_type")
    @JsonUnwrapped
    private CLTypeURef clType = new CLTypeURef();

    public CLValueURef(URef value) {
        this.setValue(value);
    }

    @Override
    public void encode(CLValueEncoder clve) throws IOException {
        // TODO: Encode
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
}
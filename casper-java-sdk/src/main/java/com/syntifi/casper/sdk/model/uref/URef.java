package com.syntifi.casper.sdk.model.uref;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.syntifi.casper.sdk.exception.DynamicInstanceException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * URef is a tuple that contains the address of the URef and the access rights 
 * to that URef. The serialized representation of the URef is 33 bytes long. 
 * The first 32 bytes are the byte representation of the URef address, and the 
 * last byte contains the bits corresponding to the access rights of the URef. 
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 * @See CLValueURef
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class URef {
    byte[] address;
    URefAccessRight accessRight;

    @JsonCreator
    public static URef fromString(String uref) throws IOException, DecoderException, DynamicInstanceException {
        if (!uref.substring(0, 5).equals("uref-")){
            throw new IOException("Not a valid Uref");
        }
        byte[] address = Hex.decodeHex(uref.substring(5,37));
        URefAccessRight accessRight = URefAccessRight.getTypeBySerializationTag(
            Hex.decodeHex(uref.substring(38))[0]);
        return new URef(address, accessRight);

    }
}

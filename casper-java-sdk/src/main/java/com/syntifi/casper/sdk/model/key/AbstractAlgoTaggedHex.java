package com.syntifi.casper.sdk.model.key;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;

import lombok.Data;

@Data
public abstract class AbstractAlgoTaggedHex {
    /**
     * @see Algorithm
     */
    @JsonIgnore
    private Algorithm algorithm;

    /**
     * Hex-encoded cryptographic public key
     */
    @JsonIgnore
    private byte[] key;

    @JsonValue
    public String getAlgoTaggedHex(){
        return StringByteHelper.convertBytesToHex(new byte[] {this.algorithm.getTag()}) + 
                StringByteHelper.convertBytesToHex(this.getKey());
    }


}

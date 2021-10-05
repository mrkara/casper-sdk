package com.syntifi.casper.sdk.model.key;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractAlgoTaggedHex {
    /**
     * @see Algorithm
     */
    private Algorithm algorithm;

    /**
     * Hex-encoded cryptographic public key
     */
    private byte[] key;

}

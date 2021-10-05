package com.syntifi.casper.sdk.model.transfer;

import lombok.Data;

/**
 * A withdraw.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Withdraw {

    /**
     * amount(String) - Decimal representation of a 512-bit integer.
     */
    private String amount;

    // TODO: Change to URef
    /**
     * bonding_purse(String) - Hex-encoded, formatted URef.
     */
    private String bondingPurse;

    /**
     * amount(Integer) - Era ID newtype.
     */
    private int eraOfCreation;

    /**
     * unbonder_public_key(String) - Hex-encoded cryptographic public key, including
     * the algorithm tag prefix.
     */
    private String unbonderPublicKey;

    /**
     * validator_public_key(String) - Hex-encoded cryptographic public key,
     * including the algorithm tag prefix.
     */
    private String validatorPublicKey;
}

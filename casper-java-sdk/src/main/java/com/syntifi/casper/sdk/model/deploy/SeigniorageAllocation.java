package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.key.PublicKey;

import lombok.Data;

/**
 * Info about a seigniorage allocation for a validator
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class SeigniorageAllocation {

    /**
     * Allocated amount
     */
    private BigInteger amount;

    /**
     * Validator's public key
     * @see PublickKey
     */
    @JsonProperty("validator_public_key")
    private PublicKey validatorPublicKey;
    
}

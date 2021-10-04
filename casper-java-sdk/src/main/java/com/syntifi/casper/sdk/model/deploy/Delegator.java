package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.key.PublicKey;

import lombok.Data;

/**
 * Represents a party delegating their stake to a validator (or \"delegatee\")
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Delegator {
    /**
     * @see PublicKey
     */
    @JsonProperty("validator_public_key")
    private PublicKey validatorPublicKey;

    /**
     * @see VestingSchedule
     */
    @JsonProperty("vesting_schedule")
    private VestingSchedule vestingSchedule;

    /**
     * @see Uref
     */
    @JsonProperty("bonding_purse")
    private String bondingPurse;
  
    /**
     * @see PublicKey
     */
    @JsonProperty("delegator_public_key")
    private PublicKey delegatorPublicKey;

    /**
     * ammount
     */
    @JsonProperty("staked_amount")
    private BigInteger stakedAmount;
}

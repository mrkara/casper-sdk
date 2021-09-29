package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.key.PublicKey;
import com.syntifi.casper.sdk.model.uref.URef;

import lombok.Data;

/**
 * An entry in the validator map.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Bid {

    /**
     * The purse that was used for bonding.
     */
    @JsonProperty("bonding_purse")
    private URef bondingPurse;

    /**
     * Delegation rate
     */
    @JsonProperty("delegation_rate")
    private int delegationRate;

    /**
     * This validator's delegators, indexed by their public keys
     */
    private HashMap<PublicKey, Delegator> delegators;

    /**
     * `true` if validator has been \"evicted\"
     */
    private boolean inactive;

    /**
     * The amount of tokens staked by a validator (not including delegators).
     */
    @JsonProperty("staked_amount")
    private BigInteger stakedAmount;

    /**
     * Validator PublicKey
     */
    @JsonProperty("validator_public_key")
    private PublicKey validatorPublicKey;

    /**
     * Vesting schedule for a genesis validator. `None` if non-genesis validator.
     */
    @JsonProperty("vesting_schedule")
    private VestingSchedule vestingSchedule;

}

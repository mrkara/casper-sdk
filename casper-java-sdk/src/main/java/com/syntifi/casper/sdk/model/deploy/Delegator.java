package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.key.PublicKey;

import lombok.Data;

@Data
public class Delegator {

    @JsonProperty("bonding_purse")
    private String bondingPurse;
   
    @JsonProperty("delegator_public_key")
    private PublicKey delegatorPublicKey;

    @JsonProperty("staked_amount")
    private BigInteger stakedAmount;

    @JsonProperty("validator_public_key")
    private PublicKey validatorPublicKey;

    @JsonProperty("vesting_schedule")
    private VestingSchedule vestingSchedule;
}

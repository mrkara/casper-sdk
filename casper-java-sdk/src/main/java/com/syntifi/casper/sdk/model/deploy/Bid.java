package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.key.PublicKey;

import org.apache.commons.codec.DecoderException;

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
    //TODO: convert to URef
    @JsonProperty("bonding_purse")
    private String bondingPurse;

    /**
     * Delegation rate
     */
    @JsonProperty("delegation_rate")
    private int delegationRate;

    /**
     * This validator's delegators, indexed by their public keys
     */
    //@JsonDeserialize(as= HashMap.class, keyAs = PublicKey.class, contentAs = Delegator.class)
    @JsonIgnore
    private Map<PublicKey, Delegator> delegators = new HashMap<>();
    @JsonProperty("delegators")
    private void unpackNested(Map<String, Delegator> node) throws DecoderException, NoSuchAlgorithmException{
        for (Map.Entry<String, Delegator> entry : node.entrySet()){
            PublicKey publicKey = PublicKey.fromTaggedHexString(entry.getKey());
            Delegator delegator = entry.getValue();
            this.delegators.put(publicKey, delegator);
        }
    }

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

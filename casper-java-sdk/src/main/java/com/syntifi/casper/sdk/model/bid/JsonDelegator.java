package com.syntifi.casper.sdk.model.bid;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.key.PublicKey;
import com.syntifi.casper.sdk.model.uref.URef;

import lombok.Data;

/**
 * A delegator associated with the give validator 
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class JsonDelegator {
    /**
     * @see URef
     */
    @JsonProperty("bonding_purse")
    private URef bondingPurse;
  
    /**
     * @see PublicKey
     */
    private PublicKey delegatee;

    /**
     * @see PublicKey 
     */
    @JsonProperty("public_key")
    private PublicKey publicKey;

    /**
     * ammount
     */
    @JsonIgnore
    private BigInteger stakedAmount;

    @JsonProperty("staked_amount")
    protected String getStakedAmount() {
        return this.stakedAmount.toString(10);
    }

    @JsonProperty("staked_amount")
    protected void setStakedAmount(String value) {
        this.stakedAmount = new BigInteger(value, 10);
    }

}

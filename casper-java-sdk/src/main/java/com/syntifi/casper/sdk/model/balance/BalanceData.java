package com.syntifi.casper.sdk.model.balance;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Root class for a Casper balance data request 
 * Result for "state_get_balance" RPC response
 *  
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class BalanceData {

    /**
     * The RPC API version 
     */
    @JsonProperty("api_version")
    private String apiVersion;

    /**
     * The balance value
     */
    @JsonProperty("balance_value")
    private BigInteger auctionState;

    /**
     * The merkle proof
     */
    @JsonProperty("merkle_proof")
    private String merkleProof;

}

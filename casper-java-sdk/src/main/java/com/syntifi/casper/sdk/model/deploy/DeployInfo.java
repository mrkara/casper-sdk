package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.uref.URef;

import lombok.Data;

/**
 * Information relating to the given Deploy
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class DeployInfo {
 
    /**
     * The relevant Deploy
     */
    @JsonProperty("deploy_hash")
    private String hash;

    /**
     * Account identifier of the creator of the Deploy.
     */
    private String from;

    /**
     * Gas cost of executing the Deploy.
     */
    private BigInteger gas;

    /**
     * Source purse used for payment of the Deploy.
     * @see URef
     */
    //TODO: CHANGE TO URef later
    private String source;

    /**
     * Transfers performed by the Deploy.
     */
    private List<String> transfers;

}

package com.syntifi.casper.sdk.model.deploy;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private BigInteger gas;

    @JsonProperty("gas")
    protected String getBigInteger() {
        return this.gas.toString(10);
    }

    @JsonProperty("gas")
    protected void setBigInteger(String value) {
        this.gas = new BigInteger(value, 10);
    }

    /**
     * Source purse used for payment of the Deploy.
     * @see URef
     */
    private URef source;

    /**
     * Transfers performed by the Deploy.
     */
    private List<String> transfers;

}

package com.syntifi.casper.sdk.model.deploy.transform;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.deploy.UnbondingPurse;

import lombok.Data;

/**
 * An AbstractExecutableDeployItem of Type ModuleBytes containing the runtime 
 * args of the contract.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class WriteWithdraw implements Transform {
   
    /**
     * Hex-encoded account hash 
     */
    @JsonProperty("WriteWithdraw")
    private List<UnbondingPurse> purses;

}



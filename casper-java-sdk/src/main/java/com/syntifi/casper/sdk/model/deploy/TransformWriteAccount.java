package com.syntifi.casper.sdk.model.deploy;
    
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class TransformWriteAccount implements Transform {
   
    /**
     * Hex-encoded account hash 
     */
    @JsonProperty("WriteAccount")
    private String account;

}



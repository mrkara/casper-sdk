package com.syntifi.casper.sdk.model.deploy.transform;
    
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.clvalue.CLValue;

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
@JsonTypeName("WriteCLValue")
public class WriteCLValue implements Transform {
   
    /**
     * @see CLValue 
     */
    //@JsonUnrapped
    @JsonProperty("WriteCLValue")
    private CLValue<?, ?> clvalue;

}

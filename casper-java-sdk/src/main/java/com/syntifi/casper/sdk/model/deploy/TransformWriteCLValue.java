package com.syntifi.casper.sdk.model.deploy;
    
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.storedvalue.clvalue.AbstractCLValue;

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
public class TransformWriteCLValue implements Transform {
   
    /**
     * @see CLValue 
     */
    //@JsonUnrapped
    @JsonProperty("WriteCLValue")
    private AbstractCLValue<?> clvalue;

}

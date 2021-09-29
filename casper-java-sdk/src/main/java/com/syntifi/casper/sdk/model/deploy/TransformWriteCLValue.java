package com.syntifi.casper.sdk.model.deploy;
    
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
public class TransformWriteCLValue extends Transform {
   
    /**
     * @see CLValue 
     */
    @JsonUnwrapped
    private AbstractCLValue<?> clvalue;

}

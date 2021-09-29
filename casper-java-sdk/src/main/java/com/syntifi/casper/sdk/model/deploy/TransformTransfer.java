package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.syntifi.casper.sdk.model.transfer.Transfer;

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
@JsonTypeName("Transfer")
public class TransformTransfer extends Transform {
   
    /**
     * @see Transfer 
     */
    @JsonUnwrapped
    private Transfer transfer;

}



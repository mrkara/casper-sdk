package com.syntifi.casper.sdk.model.deploy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

/**
 * An AbstractExecutableDeployItem of Type Transfer containing the runtime 
 * args of the contract.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonTypeName("Transfer")
public class ExecutableDeployItemTransfer extends ExecutableDeployItem{
   
    /**
     * List of @see NamedArg 
     */
    private List<NamedArg<?>> args;

}
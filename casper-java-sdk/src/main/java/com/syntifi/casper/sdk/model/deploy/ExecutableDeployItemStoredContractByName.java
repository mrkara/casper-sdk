package com.syntifi.casper.sdk.model.deploy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

/**
 * An AbstractExecutableDeployItem of Type StoredContractByName containing the runtime 
 * args of the contract.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonTypeName("StoredContractByName")
public class ExecutableDeployItemStoredContractByName extends ExecutableDeployItem{
   
    /**
     * List of @see NamedArg 
     */
    private List<NamedArg<?>> args;

    /**
     * Entry Point 
     */
    @JsonProperty("entry_point")
    private String entryPoint;

    /**
     * Contract name 
     */
    private String name;
}
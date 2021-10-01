package com.syntifi.casper.sdk.model.deploy.executabledeploy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.deploy.NamedArg;

import lombok.Data;

/**
 * An AbstractExecutableDeployItem of Type StoredContractByHash containing the runtime 
 * args of the contract.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonTypeName("StoredContractByHash")
public class StoredContractByHash implements ExecutableDeployItem{
   
    /**
     * List of @see NamedArg 
     */
    private List<NamedArg<?, ?>> args;

    /**
     * Entry Point 
     */
    @JsonProperty("entry_point")
    private String entryPoint;

    /**
     * Hex-encoded Hash 
     */
    private String hash;
}
package com.syntifi.casper.sdk.model.deploy.executabledeploy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.model.deploy.NamedArg;

import lombok.Data;


/**
 * Abstract Executable Deploy Item containing the StoredContractByName.
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 * @see ExecutableDeployItem 
 */
@Data
@JsonTypeName("StoredContractByName")
public class StoredContractByName implements ExecutableDeployItem{
   
    /**
     * @see NamedArg 
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
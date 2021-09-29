package com.syntifi.casper.sdk.model.deploy.transform;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.contract.NamedKey;

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
public class AddKeys implements Transform {
   
    /**
     *  
     */
    @JsonProperty("AddKeys")
    private List<NamedKey> addKeys;

}




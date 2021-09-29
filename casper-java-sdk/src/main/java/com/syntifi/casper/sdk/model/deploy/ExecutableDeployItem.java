package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Abstract Executable Deploy Item containing the runtime args of the contract.
 * It can be any of the following types: 
 * @see ExecutableDeployItemModuleBytes
 * @see ExecutableDeployItemStoredContractByHash
 * @see ExecutableDeployItemStoredContractByName
 * @see ExecutableDeployItemStoredVersionedContractByHash
 * @see ExecutableDeployItemStoredVersionedContractByName
 * @see ExecutableDeployItemTransfer
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ExecutableDeployItemModuleBytes.class, name = "ModuleBytes"),
    @JsonSubTypes.Type(value = ExecutableDeployItemStoredContractByHash.class, name = "StoredContractByHash"),
    @JsonSubTypes.Type(value = ExecutableDeployItemStoredContractByName.class, name = "StoredContractByName"),
    @JsonSubTypes.Type(value = ExecutableDeployItemStoredVersionedContractByHash.class, name = "StoredVersionedContractByHash"),
    @JsonSubTypes.Type(value = ExecutableDeployItemStoredVersionedContractByName.class, name = "StoredVersionedContractByName"),
    @JsonSubTypes.Type(value = ExecutableDeployItemTransfer.class, name = "Transfer")
})
public abstract class ExecutableDeployItem {
}

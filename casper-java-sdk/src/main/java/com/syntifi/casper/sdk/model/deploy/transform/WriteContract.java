package com.syntifi.casper.sdk.model.deploy.transform;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

/**
 * The type of operation performed while executing a deploy.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public enum WriteContract implements Transform {
    IDENTITY("Identity"), 
    WRITE_CONTRACT_WASM("WriteContractWasm"), 
    WRITE_CONTRACT("WriteContract"), 
    WRITE_CONTRACT_PACKAGE("WriteContractPackage");
    
    private final String tag;

    private WriteContract(String tag) {
        this.tag = tag;
    }

    @JsonCreator
    public static WriteContract getByTag(String tag) throws NoSuchTypeException {
        for (WriteContract a : values()) {
            if (a.tag.equals(tag))
                return a;
        }
        throw new NoSuchTypeException("WriteContract not valid");
    }
}

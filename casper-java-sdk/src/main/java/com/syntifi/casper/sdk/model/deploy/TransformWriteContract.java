package com.syntifi.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

/**
 * The type of operation performed while executing a deploy.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public enum TransformWriteContract implements Transform {
    IDENTITY("Identity"), 
    WRITE_CONTRACT_WASM("WriteContractWasm"), 
    WRITE_CONTRACT("WriteContract"), 
    WRITE_CONTRACT_PACKAGE("WriteContractPackage");
    
    private final String tag;

    private TransformWriteContract(String tag) {
        this.tag = tag;
    }

    @JsonCreator
    public static TransformWriteContract getByTag(String tag) throws NoSuchTypeException {
        for (TransformWriteContract a : values()) {
            if (a.tag.equals(tag))
                return a;
        }
        throw new NoSuchTypeException("WriteContract not valid");
    }
}

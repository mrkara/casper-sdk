package com.syntifi.casper.sdk.model.deploy.transform;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntifi.casper.sdk.model.transfer.Transfer;

import lombok.Data;

/**
 * An implmentation of Transform that Writes the given Transfer to global state.
 * @see Transform
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class WriteTransfer implements Transform {
   
    /**
     * @see Transfer 
     */
    @JsonProperty("WriteTransfer")
    private Transfer transfer;

}



package com.syntifi.casper.sdk.model.deploy.transform;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * An implmentation of Transform that Adds the given `u256`
 * @see Transform
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class AddUInt256 implements Transform {
   
    /**
     * u256 
     */
    @JsonProperty("AddUInt256")
    private BigInteger addUInt256;

}



package com.syntifi.casper.sdk.model.deploy.transform;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

/**
 * An implmentation of Transform that Adds the given `u64`
 * @see Transform
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonTypeName("AddUInt64")
public class AddUInt64 implements Transform {
   
    /**
     * u64 
     */
    @JsonProperty("AddUInt64")
    private BigInteger addUInt64;

}



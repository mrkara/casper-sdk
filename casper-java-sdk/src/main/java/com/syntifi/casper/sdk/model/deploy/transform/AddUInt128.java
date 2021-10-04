package com.syntifi.casper.sdk.model.deploy.transform;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

/**
 * An implmentation of Transform that Adds the given `u128`
 * @see Transform
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
@JsonTypeName("AddUInt128")
public class AddUInt128 implements Transform {
   
    /**
     * u128
     */
    @JsonProperty("AddUInt128")
    private BigInteger addUInt128;

}




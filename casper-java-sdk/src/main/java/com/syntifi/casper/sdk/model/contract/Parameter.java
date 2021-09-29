package com.syntifi.casper.sdk.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A named key.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Data
public class Parameter {
    /**
     * The value of the entry: a casper `Key` type.
     */
    @JsonProperty("cl_type")
    private String clType;

    /**
     * The name of the entry.
     */
    @JsonProperty("name")
    private String name;
}


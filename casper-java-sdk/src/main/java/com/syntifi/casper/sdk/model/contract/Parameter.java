package com.syntifi.casper.sdk.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.syntifi.casper.sdk.jackson.CLTypeResolver;
import com.syntifi.casper.sdk.model.clvalue.type.CLType;

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
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    @JsonTypeResolver(CLTypeResolver.class)
    private CLType clType;

    /**
     * The name of the entry.
     */
    @JsonProperty("name")
    private String name;
}

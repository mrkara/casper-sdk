package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CLType for {@link CLType.I32}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeI32 extends CLTypeBasic {
    @JsonProperty("cl_type")
    private final String typeName = CLType.I32;
}

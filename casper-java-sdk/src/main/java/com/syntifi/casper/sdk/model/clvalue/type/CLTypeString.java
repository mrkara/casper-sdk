package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CLType for {@link CLType.STRING}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeString extends CLTypeBasic {
    @JsonProperty("cl_type")
    private final String typeName = CLType.STRING;
}

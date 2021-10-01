package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CLType for {@link CLType.U64}
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see CLType
 * @since 0.0.1
 */

@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeU64 extends CLTypeBasic {
    @JsonProperty("cl_type")
    private final String typeName = CLType.U64;
}

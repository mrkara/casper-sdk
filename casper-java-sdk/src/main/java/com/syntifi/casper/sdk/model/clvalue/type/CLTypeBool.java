package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true, of = { "typeName" })
public class CLTypeBool extends CLTypeBasic {
    @JsonProperty("cl_type")
    private final String typeName = CLType.BOOL;
}

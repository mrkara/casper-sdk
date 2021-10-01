package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false, of = { "typeName" })
public class CLTypeAny extends CLTypeBasic {
    @JsonProperty("cl_type")
    private final String typeName = CLType.ANY;
}

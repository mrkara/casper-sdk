package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CLTypeURef extends CLTypeBasic {
    @JsonProperty("cl_type")
    private final String typeName = CLType.UREF;
}

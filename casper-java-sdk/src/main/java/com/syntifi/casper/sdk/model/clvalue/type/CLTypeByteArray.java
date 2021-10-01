package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CLTypeByteArray extends CLType {
    @JsonIgnore
    private final String typeName = CLType.BYTE_ARRAY;

    @Setter
    @JsonProperty(CLType.BYTE_ARRAY)
    private int length;
}

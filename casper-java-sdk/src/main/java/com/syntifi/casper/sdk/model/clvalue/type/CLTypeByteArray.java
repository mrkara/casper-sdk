package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = true, of = { "typeName", "length" })
public class CLTypeByteArray extends CLTypeBasic {
    @JsonIgnore
    private final String typeName = CLType.BYTE_ARRAY;

    @Setter
    @JsonProperty(CLType.BYTE_ARRAY)
    private int length;
}

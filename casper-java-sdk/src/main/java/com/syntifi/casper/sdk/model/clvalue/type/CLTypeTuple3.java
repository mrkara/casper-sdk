package com.syntifi.casper.sdk.model.clvalue.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CLTypeTuple3 extends CLType {
    @JsonIgnore
    private String typeName = CLType.TUPLE3;

    @JsonProperty("ByteArray")
    private int length;
}
